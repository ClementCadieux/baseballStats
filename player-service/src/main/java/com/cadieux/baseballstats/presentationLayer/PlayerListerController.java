package com.cadieux.baseballstats.presentationLayer;

import com.cadieux.baseballstats.businessLayer.PlayerFinderService;
import com.cadieux.baseballstats.mapping.Player.PlayerRequestModel;
import com.cadieux.baseballstats.mapping.Player.PlayerResponseModel;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.PlayerCareerResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PlayerListerController {
    private PlayerFinderService playerFinderService;

    public PlayerListerController(PlayerFinderService playerFinderService){
        this.playerFinderService = playerFinderService;
    }

    @GetMapping("/players")
    public ResponseEntity<List<PlayerResponseModel>> getAllPlayers(){
        List<PlayerResponseModel> playerResponseModels = playerFinderService.findAllPlayers();

        return ResponseEntity.status(HttpStatus.OK).body(playerResponseModels);
    }

    @GetMapping("/players/{playerId}")
    public ResponseEntity<PlayerResponseModel> getPlayerByPlayerId(@PathVariable Integer playerId){
        PlayerResponseModel playerResponseModel = playerFinderService.findPlayerByPlayerId(playerId);

        return ResponseEntity.status(HttpStatus.OK).body(playerResponseModel);
    }

    @PostMapping("/players")
    public ResponseEntity<PlayerResponseModel> createPlayer(@RequestBody PlayerRequestModel playerRequestModel){
        PlayerResponseModel playerResponseModel = playerFinderService.createPlayer(playerRequestModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(playerResponseModel);
    }

    @PutMapping("/players/{playerId}")
    public ResponseEntity<PlayerResponseModel> updatePlayer(@RequestBody PlayerRequestModel playerRequestModel, @PathVariable Integer playerId){
        PlayerResponseModel playerResponseModel = playerFinderService.updatePlayer(playerRequestModel, playerId);

        return ResponseEntity.status(HttpStatus.OK).body(playerResponseModel);
    }

    @DeleteMapping("/players/{playerId}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer playerId){
        playerFinderService.deletePlayer(playerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/players/{playerId}/stats")
    public ResponseEntity<PlayerCareerResponseModel> getPlayerCareer(@PathVariable Integer playerId){
        PlayerCareerResponseModel player = playerFinderService.getPlayerCareer(playerId);

        return ResponseEntity.status(HttpStatus.OK).body(player);
    }

    @GetMapping("/players/{playerId}/seasons")
    public ResponseEntity<List<SeasonResponseModel>> getPlayerSeasons(@PathVariable Integer playerId){
        List<SeasonResponseModel> seasons = playerFinderService.getSeasonsByPlayerId(playerId);

        return ResponseEntity.status(HttpStatus.OK).body(seasons);
    }

    @GetMapping("/teams/{teamId}/players")
    public ResponseEntity<List<PlayerResponseModel>> getPlayersByTeam(@PathVariable Integer teamId){
        List<PlayerResponseModel> players = playerFinderService.getPlayersByTeamId(teamId);

        return ResponseEntity.status(HttpStatus.OK).body(players);
    }

    @DeleteMapping("/teams/{teamId}/players")
    public ResponseEntity<?> deletePLayers(@PathVariable Integer teamId){
        playerFinderService.deletePlayersByTeam(teamId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
