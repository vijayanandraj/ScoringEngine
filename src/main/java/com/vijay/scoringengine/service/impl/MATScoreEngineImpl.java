package com.vijay.scoringengine.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vijay.scoringengine.entity.WorkloadScore;
import com.vijay.scoringengine.repository.NativeQueryRepository;
import com.vijay.scoringengine.repository.WorkloadScoreRepository;
import com.vijay.scoringengine.service.MATScoreEngine;
import com.vijay.scoringengine.service.scoring.ScoringStrategy;
import com.vijay.scoringengine.service.scoring.ScoringStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * Implementation of MATScoreEngine
 */
@Service
@Slf4j
public class MATScoreEngineImpl implements MATScoreEngine {

    @Autowired
    private ScoringStrategyFactory scoringStrategyFactory;

    @Autowired
    private WorkloadScoreRepository workloadScoreRepository;

    @Autowired
    private NativeQueryRepository nativeQueryRepository;

    @Override
    public int calculateScanScore(String scanEngine, Integer ait_id, String component_id) {
        ScoringStrategy.ScoringStrategyType scoringStrategyType = ScoringStrategy.ScoringStrategyType.valueOf(scanEngine);
        log.debug("Scoring strategy type ==> " + scoringStrategyType.toString());
        ScoringStrategy scoringStrategy = scoringStrategyFactory.findStrategy(scoringStrategyType);
        return scoringStrategy.calculateScanScore(ait_id, component_id);
    }

    @Override
    public int calculateBusinessCriticalityScore(Integer ait_id, Integer workloadCrticalityScore){
        int businessCriticalityScore = 0;
        List<Object[]> rows = nativeQueryRepository.findAppStrategyAndCriticalityScore(ait_id);
        if(rows != null){
            for(Object[] row : rows){
                String appStrategy = row[0].toString();
                Float criticalityScore = Float.parseFloat(row[1].toString());
                int appStrategyScore = STRATEGY_SCORE_MAP.getOrDefault(appStrategy, 0);
                Float criticalityScoreFloat = appStrategyScore + (criticalityScore * 40);
                businessCriticalityScore = (int)Math.round((criticalityScoreFloat + (workloadCrticalityScore * 4))/10);
                log.info("Business criticality score for AIT : {} ==> {}", ait_id, businessCriticalityScore);
            }
        }
        return businessCriticalityScore;
    }

    @Override
    public WorkloadScore save_workload_score(WorkloadScore workloadScore){
        //TODO - Find workload entry in DB and update the score
        return workloadScoreRepository.save(workloadScore);

    }

    @Override
    public int calculateSurveyScore(String jsonString, Integer workloadID) {
        ObjectMapper mapper = new ObjectMapper();
        char delimeter =  '|';
        int surveyScore = 0;
        int totalQuestions = 0;
        try {
            JsonNode rootNode = mapper.readTree(jsonString);
            for (JsonNode field : rootNode) {
                String fieldName = field.fieldNames().next();
                String fieldValue = field.get(fieldName).asText();
                System.out.println(fieldName + ": " + fieldValue);
                if(fieldValue.contains(Character.toString(delimeter))){
                    String[] values = fieldValue.split(Character.toString(delimeter));
                    surveyScore = surveyScore + Integer.parseInt(values[0]);
                    totalQuestions = totalQuestions + 1;
                }
            }
            //Calculates the raw percentage score, and then converts it to a percentage between 0 and 100 by multiplying by 100.
            //The multiplication by 5 in the denominator of the expression (totalQuestions * 5) is necessary because each
            // question is worth 5 points, and the total number of points possible is equal to the number of
            // questions multiplied by 5.
            double percentageScore = (double)  surveyScore / (totalQuestions * 5) * 100;
            surveyScore = (int) Math.round(percentageScore / 100 * MATScoreEngine.TOTAL_SURVEY_SCORE);
            log.info("Survey score for workload : {} ==> {}", workloadID, surveyScore);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON string", e);
        }
        return surveyScore;
    }

    @Override
    public int getWorkloadScore(Integer workloadID) {
        return 0;
    }

    @Override
    public int getWorkloadSurveyScore(Integer workloadID) {
        return 0;
    }

    @Override
    public int getWorkloadScanScore(Integer workloadID) {
        return 0;
    }
}
