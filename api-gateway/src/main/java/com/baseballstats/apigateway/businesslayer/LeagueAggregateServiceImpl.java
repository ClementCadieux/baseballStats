package com.baseballstats.apigateway.businesslayer;

import com.baseballstats.apigateway.domainclientlayer.LeagueServiceClient;
import com.baseballstats.apigateway.domainclientlayer.PlayerServiceClient;
import com.baseballstats.apigateway.domainclientlayer.TeamServiceClient;
import com.baseballstats.apigateway.mappinglayer.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LeagueAggregateServiceImpl implements LeagueAggregateService{

    private TeamServiceClient teamServiceClient;
    private PlayerServiceClient playerServiceClient;
    private LeagueServiceClient leagueServiceClient;
    private LeagueAggregateMapper leagueAggregateMapper;
    private TeamSummaryMapper teamSummaryMapper;

    public LeagueAggregateServiceImpl(TeamServiceClient teamServiceClient, PlayerServiceClient playerServiceClient, LeagueServiceClient leagueServiceClient, LeagueAggregateMapper leagueAggregateMapper, TeamSummaryMapper teamSummaryMapper) {
        this.teamServiceClient = teamServiceClient;
        this.playerServiceClient = playerServiceClient;
        this.leagueServiceClient = leagueServiceClient;
        this.leagueAggregateMapper = leagueAggregateMapper;
        this.teamSummaryMapper = teamSummaryMapper;
    }

    @Override
    public LeagueAggregate getLeagueById(Integer leagueId) {
        LeagueResponseModel leagueResponseModel = leagueServiceClient.getLeagueById(leagueId);

        List<TeamDetailsResponseModel> teamDetailsResponseModels = teamServiceClient.getTeamsByLeagueId(leagueId);

        List<TeamSummaryModel> teamSummaryModels = teamSummaryMapper.responseModelListToSummaryModelList(teamDetailsResponseModels);

        LeagueAggregate leagueAggregate = leagueAggregateMapper.responseModelToAggregate(leagueResponseModel);

        leagueAggregate.setTeamSummaryModels(teamSummaryModels);

        return leagueAggregate;
    }

    @Override
    public LeagueAggregate createLeague(LeagueAggregateRequestModel leagueAggregateRequestModel) {
        LeagueRequestModel leagueRequestModel = new LeagueRequestModel();

        leagueRequestModel.setCategory(leagueAggregateRequestModel.getCategory());
        leagueRequestModel.setName(leagueAggregateRequestModel.getName());

        LeagueResponseModel leagueResponseModel = leagueServiceClient.createLeague(leagueRequestModel);

        List<TeamSummaryModel> teamSummaryModels = leagueAggregateRequestModel.getTeamSummaryModels();

        List<TeamDetailsResponseModel> teamResponseModels = new ArrayList<>();

        for (TeamSummaryModel teamSummaryModel:
             teamSummaryModels) {
            TeamRequestModel teamRequestModel = new TeamRequestModel();

            teamRequestModel.setLeagueId(leagueResponseModel.getLeagueId());
            teamRequestModel.setCity(teamSummaryModel.getCity());
            teamRequestModel.setName(teamRequestModel.getName());

            teamResponseModels.add(teamServiceClient.createTeam(teamRequestModel));
        }

        List<TeamSummaryModel> teamSummaryModelList = teamSummaryMapper.responseModelListToSummaryModelList(teamResponseModels);

        LeagueAggregate leagueAggregate = leagueAggregateMapper.responseModelToAggregate(leagueResponseModel);

        leagueAggregate.setTeamSummaryModels(teamSummaryModelList);

        return leagueAggregate;
    }

    @Override
    public LeagueAggregate updateLeague(LeagueRequestModel leagueRequestModel, Integer leagueId) {
        LeagueResponseModel leagueResponseModel = leagueServiceClient.updateLeague(leagueRequestModel, leagueId);

        List<TeamDetailsResponseModel> teamDetailsResponseModels = teamServiceClient.getTeamsByLeagueId(leagueId);

        List<TeamSummaryModel> teamSummaryModelList = teamSummaryMapper.responseModelListToSummaryModelList(teamDetailsResponseModels);

        LeagueAggregate leagueAggregate = leagueAggregateMapper.responseModelToAggregate(leagueResponseModel);

        leagueAggregate.setTeamSummaryModels(teamSummaryModelList);

        return leagueAggregate;
    }

    @Override
    public void deleteLeague(Integer leagueId) {
        List<TeamDetailsResponseModel> existingTeams = teamServiceClient.getTeamsByLeagueId(leagueId);

        for (TeamDetailsResponseModel teamDetailsResponseModel:
                existingTeams) {
            playerServiceClient.deletePlayers(teamDetailsResponseModel.getTeamId());
        }

        teamServiceClient.deleteTeamsByLeagueId(leagueId);
        leagueServiceClient.deleteLeague(leagueId);
    }
}
