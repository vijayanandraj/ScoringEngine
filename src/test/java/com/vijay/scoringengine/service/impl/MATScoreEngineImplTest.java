package com.vijay.scoringengine.service.impl;


import com.vijay.scoringengine.entity.WorkloadScore;
import com.vijay.scoringengine.repository.NativeQueryRepository;
import com.vijay.scoringengine.repository.WorkloadScoreRepository;
import com.vijay.scoringengine.service.MATScoreEngine;
import com.vijay.scoringengine.service.scoring.ScoringStrategy;
import com.vijay.scoringengine.service.scoring.ScoringStrategyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MATScoreEngineImplTest {

    @InjectMocks
    private MATScoreEngineImpl matScoreEngineImpl;

    @Mock
    private ScoringStrategyFactory scoringStrategyFactory;

    @Mock
    private WorkloadScoreRepository workloadScoreRepository;

    @Mock
    private NativeQueryRepository nativeQueryRepository;

    // Test cases to be added here

    @Test
    public void testCalculateScanScore() {
        String scanEngine = "SCAN_ENGINE_A";
        Integer ait_id = 1;
        String component_id = "component1";

        ScoringStrategy.ScoringStrategyType scoringStrategyType = ScoringStrategy.ScoringStrategyType.valueOf(scanEngine);
        ScoringStrategy mockScoringStrategy = mock(ScoringStrategy.class);

        when(scoringStrategyFactory.findStrategy(scoringStrategyType)).thenReturn(mockScoringStrategy);
        when(mockScoringStrategy.calculateScanScore(ait_id, component_id)).thenReturn(95);

        int scanScore = matScoreEngineImpl.calculateScanScore(scanEngine, ait_id, component_id);

        assertEquals(95, scanScore);
        verify(scoringStrategyFactory, times(1)).findStrategy(scoringStrategyType);
        verify(mockScoringStrategy, times(1)).calculateScanScore(ait_id, component_id);
    }


    @Test
    public void testCalculateSurveyScore() {
        String jsonString = "{\"question1\":\"4|Strongly Agree\",\"question2\":\"3|Agree\",\"question3\":\"2|Neutral\"}";
        Integer workloadID = 1;

        int expectedSurveyScore = (int) Math.round(((4.0 + 3 + 2) / (3 * 5)) * 100 / 100 * MATScoreEngine.TOTAL_SURVEY_SCORE);

        int surveyScore = matScoreEngineImpl.calculateSurveyScore(jsonString, workloadID);

        assertEquals(expectedSurveyScore, surveyScore);
    }

    @Test
    public void testCalculateBusinessCriticalityScore() {
        Integer ait_id = 1;
        Integer workloadCriticalityScore = 10;

        List<Object[]> rows = new ArrayList<>();
        Object[] row1 = new Object[] {"STRATEGY_A", 1.5f};
        rows.add(row1);

        when(nativeQueryRepository.findAppStrategyAndCriticalityScore(ait_id)).thenReturn(rows);

        int appStrategyScore = MATScoreEngineImpl.STRATEGY_SCORE_MAP.getOrDefault(row1[0].toString(), 0);
        Float criticalityScoreFloat = appStrategyScore + (Float.parseFloat(row1[1].toString()) * 40);
        int expectedBusinessCriticalityScore = (int) Math.round((criticalityScoreFloat + (workloadCriticalityScore * 4)) / 10);

        int businessCriticalityScore = matScoreEngineImpl.calculateBusinessCriticalityScore(ait_id, workloadCriticalityScore);

        assertEquals(expectedBusinessCriticalityScore, businessCriticalityScore);
        verify(nativeQueryRepository, times(1)).findAppStrategyAndCriticalityScore(ait_id);
    }


}

