package com.baseballstats.league.datalayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LeagueRepository extends CrudRepository<League, Integer> {
    League findLeagueByLeagueId(Integer leagueId);
    boolean existsLeagueByLeagueId(Integer leagueId);
    void deleteLeagueByLeagueId(Integer leagueId);
}
