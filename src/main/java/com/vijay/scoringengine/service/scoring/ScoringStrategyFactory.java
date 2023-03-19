package com.vijay.scoringengine.service.scoring;

import com.vijay.scoringengine.service.scoring.impl.CSAScoringStrategy;
import com.vijay.scoringengine.service.scoring.impl.MTAScoringStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class ScoringStrategyFactory {
    private Map<ScoringStrategy.ScoringStrategyType, ScoringStrategy> strategies;

    @Autowired
    public ScoringStrategyFactory(Set<ScoringStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public ScoringStrategy findStrategy(ScoringStrategy.ScoringStrategyType strategyName) {
        return strategies.get(strategyName);
    }
    private void createStrategy(Set<ScoringStrategy> strategySet) {
        strategies = new HashMap<ScoringStrategy.ScoringStrategyType, ScoringStrategy>();
        strategySet.forEach(
                strategy ->strategies.put(strategy.getScoringStrategyType(), strategy));
    }

}
