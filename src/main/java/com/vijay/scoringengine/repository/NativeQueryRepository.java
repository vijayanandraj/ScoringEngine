package com.vijay.scoringengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NativeQueryRepository extends JpaRepository<Object, Long> {
    @Query(value = "select app_strategy, criticality_score from ait_score_card score, ait_extract ait where score.ait_id =  ait_extract.ait_id and score_ait_id = ?1", nativeQuery = true)
    public List<Object[]> findAppStrategyAndCriticalityScore(Integer ait_id);
}
