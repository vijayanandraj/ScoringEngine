package com.vijay.scoringengine.service.scoring;

import java.util.Map;

public interface ScoringStrategy {

    enum ScoringStrategyType {
        CSA,
        MTA
    }

    public static final int PLATFORM_READINESS_SCORE = 30;

    public static final int CLOUD_READINESS_SCORE = 20;

    /**
     * Lookup table for severity to score mapping. Simplify the code and avoid conditional statements
     */
    public static final Map<Integer, Integer> SEVERITY_SCORE_MAP = Map.of(
            1, -10,
            2, -5,
            3, -2,
            4, -1
    );

    /**
     * Calculates the platform readiness score for a given AIT and component
     * @param ait_id
     * @param component_id
     * @return
     */
    public int calculateScanScore(Integer ait_id, String component_id);


    /**
     * Returns the type of the scoring strategy
     * @return
     */
    public ScoringStrategyType getScoringStrategyType();

}
