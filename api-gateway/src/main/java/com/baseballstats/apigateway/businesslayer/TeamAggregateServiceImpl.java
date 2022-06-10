package com.baseballstats.apigateway.businesslayer;

import com.baseballstats.apigateway.domainclientlayer.PlayerServiceClient;
import com.baseballstats.apigateway.domainclientlayer.TeamServiceClient;
import com.baseballstats.apigateway.mappinglayer.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamAggregateServiceImpl implements TeamAggregateService{
    private TeamServiceClient teamServiceClient;
    private PlayerServiceClient playerServiceClient;
    private PlayerSummaryMapper playerSummaryMapper;
    private TeamAggregateMapper teamAggregateMapper;

    public TeamAggregateServiceImpl(TeamAggregateMapper teamAggregateMapper, PlayerSummaryMapper playerSummaryMapper, TeamServiceClient teamServiceClient, PlayerServiceClient playerServiceClient){
        this.teamServiceClient = teamServiceClient;
        this.playerServiceClient = playerServiceClient;
        this.playerSummaryMapper = playerSummaryMapper;
        this.teamAggregateMapper = teamAggregateMapper;
    }

    @Override
    public TeamAggregate getTeamByTeamId(Integer teamId) {
        TeamDetailsResponseModel teamDetailsResponseModel = teamServiceClient.getTeamDetails(teamId);

        List<PlayerResponseModel> playerResponseModels = playerServiceClient.getPlayersDetails(teamId);

        List<PlayerSummaryModel> playerSummaryModels = playerSummaryMapper.responseModelListToSummaryModelList(playerResponseModels);

        TeamAggregate teamAggregate = teamAggregateMapper.responseModelToAggregate(teamDetailsResponseModel);

        teamAggregate.setPlayers(playerSummaryModels);

        return teamAggregate;
    }

    @Override
    public TeamAggregate createTeam(TeamAggregateRequestModel teamAggregateRequestModel) {
        TeamRequestModel teamRequestModel = new TeamRequestModel();

        teamRequestModel.setCity(teamAggregateRequestModel.getCity());
        teamRequestModel.setName(teamAggregateRequestModel.getName());
        teamRequestModel.setLeagueId(teamAggregateRequestModel.getLeagueId());

        TeamDetailsResponseModel teamDetailsResponseModel = teamServiceClient.createTeam(teamRequestModel);

        List<PlayerSummaryModel> playerSummaryModels = teamAggregateRequestModel.getPlayers();

        List<PlayerResponseModel> playerResponseModels = new ArrayList<>();

        for (PlayerSummaryModel playerSummaryModel:
             playerSummaryModels) {
            PlayerRequestModel playerRequestModel = new PlayerRequestModel();

            playerRequestModel.setAge(playerSummaryModel.getAge());
            playerRequestModel.setFirstName(playerSummaryModel.getFirstName());
            playerRequestModel.setLastName(playerSummaryModel.getLastName());
            playerRequestModel.setPosition(playerSummaryModel.getPosition());
            playerRequestModel.setTeamId(teamDetailsResponseModel.getTeamId());

            playerResponseModels.add(playerServiceClient.createPlayer(playerRequestModel));
        }

        List<PlayerSummaryModel> playerSummaryModelsRes = playerSummaryMapper.responseModelListToSummaryModelList(playerResponseModels);

        TeamAggregate teamAggregate = teamAggregateMapper.responseModelToAggregate(teamDetailsResponseModel);

        teamAggregate.setPlayers(playerSummaryModelsRes);

        return teamAggregate;
    }

    @Override
    public TeamAggregate updateTeam(TeamAggregateRequestModel teamAggregateRequestModel, Integer teamId) {
        TeamRequestModel teamRequestModel = new TeamRequestModel();

        teamRequestModel.setCity(teamAggregateRequestModel.getCity());
        teamRequestModel.setName(teamAggregateRequestModel.getName());
        teamRequestModel.setLeagueId(teamAggregateRequestModel.getLeagueId());

        TeamDetailsResponseModel teamDetailsResponseModel = teamServiceClient.updateTeam(teamRequestModel, teamId);

        List<PlayerResponseModel> playerResponseModels = playerServiceClient.getPlayersDetails(teamId);

        List<PlayerSummaryModel> playerSummaryModelsRes = playerSummaryMapper.responseModelListToSummaryModelList(playerResponseModels);

        TeamAggregate teamAggregate = teamAggregateMapper.responseModelToAggregate(teamDetailsResponseModel);

        teamAggregate.setPlayers(playerSummaryModelsRes);

        return teamAggregate;
    }

    @Override
    public void deleteTeam(Integer teamId) {
        playerServiceClient.deletePlayers(teamId);
        teamServiceClient.deleteTeam(teamId);
    }
}
