package com.vijay.scoringengine.service.scoring.impl;

import com.vijay.scoringengine.service.scoring.ScoringStrategy;
import org.springframework.stereotype.Component;

@Component
public class CSAScoringStrategy implements ScoringStrategy {

    @Override
    public int calculateScanScore(Integer ait_id, String component_id) {
        return 0;
    }

    @Override
    public ScoringStrategyType getScoringStrategyType() {
        return ScoringStrategyType.CSA;
    }
}
