package com.baseballstats.team.businesslayer;

import com.baseballstats.team.mappinglayer.TeamRequestModel;
import com.baseballstats.team.mappinglayer.TeamResponseModel;

import java.util.List;

public interface TeamService {
    TeamResponseModel findTeam(Integer teamId);
    TeamResponseModel createTeam(TeamRequestModel teamRequestModel);
    TeamResponseModel updateTeam(TeamRequestModel teamRequestModel, Integer teamId);
    void deleteTeam(Integer teamId);
    List<TeamResponseModel> findTeamsByLeagueId(Integer leagueId);
    void deleteTeams(Integer leagueId);
}
