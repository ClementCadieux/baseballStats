package com.cadieux.baseballstats.businessLayer;

import com.cadieux.baseballstats.mapping.Player.PlayerRequestModel;
import com.cadieux.baseballstats.mapping.Player.PlayerResponseModel;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.PlayerCareerResponseModel;

import java.util.List;

public interface PlayerFinderService {
    public List<PlayerResponseModel> findAllPlayers();

    public PlayerResponseModel findPlayerByPlayerId(Integer playerId);

    public PlayerResponseModel createPlayer(PlayerRequestModel requestModel);

    public PlayerResponseModel updatePlayer(PlayerRequestModel requestModel, Integer playerId);

    public void deletePlayer(Integer playerId);

    public PlayerCareerResponseModel getPlayerCareer(Integer playerId);

    public List<SeasonResponseModel> getSeasonsByPlayerId(Integer playerId);

    public List<PlayerResponseModel> getPlayersByTeamId(Integer teamId);

    void deletePlayersByTeam(Integer teamId);
}
