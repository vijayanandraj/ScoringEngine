package com.vijay.scoringengine.repository;

import com.vijay.scoringengine.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
