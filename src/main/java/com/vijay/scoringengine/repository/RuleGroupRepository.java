package com.vijay.scoringengine.repository;

import com.vijay.scoringengine.entity.RuleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RuleGroupRepository extends JpaRepository<RuleGroup, Long> {

    RuleGroup findByGroupId(@Param("groupId") String groupId);

}
