package com.cadieux.baseballstats.dataLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PitchingStatsRepository extends CrudRepository<PitchingStats, Integer> {
    PitchingStats findPitchingStatsByPitchingId(Integer pitchingId);

    boolean existsPitchingStatsByPitchingId(Integer pitchingId);
}
