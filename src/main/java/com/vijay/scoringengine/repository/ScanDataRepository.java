package com.vijay.scoringengine.repository;

import com.vijay.scoringengine.entity.ScanData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScanDataRepository extends JpaRepository<ScanData, Long> {

    @Query(value = "SELECT DISTINCT rg.group_name, rg.severity " +
            "FROM scan_data sd " +
            "JOIN rules r ON sd.rule_id = r.rule_id " +
            "JOIN rule_groups rg ON r.group_id = rg.group_id " +
            "WHERE sd.ait_id = :aitId " +
            "AND sd.component_id = :componentId",
            nativeQuery = true)
    List<Object[]> findDistinctRuleGroupsAndSeveritiesByAitIdAndComponentId(@Param("aitId") Integer aitId, @Param("componentId") String componentId);


}