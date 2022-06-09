package com.cadieux.baseballstats.dataLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BattingStatsRepository extends CrudRepository<BattingStats, Integer> {
    BattingStats findBattingStatsByBattingId(Integer battingId);

    boolean existsBattingStatsByBattingId(Integer battingStatsId);
}
