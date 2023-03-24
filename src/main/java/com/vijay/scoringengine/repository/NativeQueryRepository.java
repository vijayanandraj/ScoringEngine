package com.vijay.scoringengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class NativeQueryRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Object[]> findAppStrategyAndCriticalityScore(Integer ait_id){
      String queryStr = "select app_strategy, criticality_score from ait_score_card score, ait_extract ait where score.ait_id =  ait_extract.ait_id and score_ait_id = ?1";
      Query nativeQuery = em.createNativeQuery(queryStr);
      nativeQuery.setParameter(1, ait_id);
      return nativeQuery.getResultList();

    }

}
