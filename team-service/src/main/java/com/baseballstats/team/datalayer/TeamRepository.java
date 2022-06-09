package com.baseballstats.team.datalayer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    Team findTeamByTeamId(Integer teamId);
    boolean existsTeamByTeamId(Integer teamId);
    List<Team> findTeamsByLeagueId(Integer leagueId);
    void deleteTeamByTeamId(Integer teamId);
    void deleteTeamsByLeagueId(Integer leagueId);
}
