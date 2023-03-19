package com.vijay.scoringengine.repository;

import com.vijay.scoringengine.entity.WorkloadScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkloadScoreRepository extends JpaRepository<WorkloadScore, Long> {
}
