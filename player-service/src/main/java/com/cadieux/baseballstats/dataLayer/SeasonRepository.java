package com.cadieux.baseballstats.dataLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonRepository extends CrudRepository<Season, Integer> {
    public Season findSeasonBySeasonId(Integer seasonId);

    boolean existsSeasonBySeasonId(Integer seasonId);

    void deleteSeasonBySeasonId(Integer seasonId);

    public List<Season> findSeasonsByPlayer(Player player);
}
