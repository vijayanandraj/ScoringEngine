package com.vijay.scoringengine.service;

import com.vijay.scoringengine.entity.WorkloadScore;

import java.util.Map;

public interface MATScoreEngine {

    public static final String MTA = "MTA";
    public static final String CSA = "CSA";

    public static final int TOTAL_SURVEY_SCORE = 50;

    public static final Map<String, Integer> STRATEGY_SCORE_MAP = Map.of(
            "Grow", 20,
            "Maintain", 10,
            "Exit", 5
    );

    /**
     * Calculates the score from code scan for a given AIT and component
     * @param ait_id
     * @param component_id
     * @return
     */
    public int calculateScanScore(String scanEngine, Integer ait_id, String component_id);

    /**
     * Calculates the business criticality score for a given AIT
     * @param ait_id
     * @return
     */
    public int calculateBusinessCriticalityScore(Integer ait_id, Integer workloadCrticalityScore);

    /**
     * Persists the overall migration readiness score (Survey score + scan score) for a given workload
     * @param workloadScore entity
     * @return
     */
    public WorkloadScore save_workload_score(WorkloadScore workloadScore);
    /**
     * Calculates the survey score for a given workload
     * @param jsonString - The answer response as JSON String
     * @param workloadID
     * @return
     */
    public int calculateSurveyScore(String jsonString, Integer workloadID);

    public int getWorkloadScore(Integer workloadID);

    public int getWorkloadSurveyScore(Integer workloadID);

    public int getWorkloadScanScore(Integer workloadID);
}
