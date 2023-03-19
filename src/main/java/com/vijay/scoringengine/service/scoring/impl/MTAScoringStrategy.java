package com.vijay.scoringengine.service.scoring.impl;

import com.vijay.scoringengine.repository.ScanDataRepository;
import com.vijay.scoringengine.service.scoring.ScoringStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This is implementation of ScoringStrategy that supports scoring of scan data from Red Hat Migration Toolkit for Applications (MTA)
 */
@Component
@Slf4j
public class MTAScoringStrategy implements ScoringStrategy {

    @Autowired
    private ScanDataRepository scanDataRepository;

    @Override
    public int calculateScanScore(Integer ait_id, String component_id) {
        List<Object[]> distinctGroups = scanDataRepository.findDistinctRuleGroupsAndSeveritiesByAitIdAndComponentId(ait_id, component_id);
        int platformScore = ScoringStrategy.PLATFORM_READINESS_SCORE;
        int  cloudScore = ScoringStrategy.CLOUD_READINESS_SCORE;
        int scanScore = 0;
        for(Object[] row : distinctGroups) {
            String ruleGroup = row[0].toString();
            int severity = Integer.parseInt(row[1].toString());
            if(ruleGroup.startsWith("CLOUD")){
                cloudScore = cloudScore + SEVERITY_SCORE_MAP.getOrDefault(severity, 0);
            }else{
                platformScore = platformScore + SEVERITY_SCORE_MAP.getOrDefault(severity, 0);
            }
        }
        log.info("Platform score for AIT : {} and Component : {} ==> {}", ait_id, component_id, platformScore);
        log.info("Cloud score for AIT : {} and Component : {} ==> {}", ait_id, component_id, cloudScore);
        scanScore = platformScore + cloudScore;
        log.info("Scan score for AIT : {} and Component : {} ==> {}", ait_id, component_id, scanScore);
        return scanScore;
    }

    @Override
    public ScoringStrategyType getScoringStrategyType() {
        return ScoringStrategyType.MTA;
    }
}
