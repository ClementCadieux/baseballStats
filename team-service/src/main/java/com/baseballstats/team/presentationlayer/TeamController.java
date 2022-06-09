package com.baseballstats.team.presentationlayer;

import com.baseballstats.team.businesslayer.TeamService;
import com.baseballstats.team.mappinglayer.TeamRequestModel;
import com.baseballstats.team.mappinglayer.TeamResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<TeamResponseModel> getTeamByTeamId(@PathVariable Integer teamId){
        TeamResponseModel teamResponseModel = teamService.findTeam(teamId);

        return ResponseEntity.status(HttpStatus.OK).body(teamResponseModel);
    }

    @PostMapping("/teams")
    public ResponseEntity<TeamResponseModel> createTeam(@RequestBody TeamRequestModel teamRequestModel){
        TeamResponseModel teamResponseModel = teamService.createTeam(teamRequestModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(teamResponseModel);
    }

    @PutMapping("/teams/{teamId}")
    public ResponseEntity<TeamResponseModel> updateTeam(@RequestBody TeamRequestModel teamRequestModel, @PathVariable Integer teamId){
        TeamResponseModel teamResponseModel = teamService.updateTeam(teamRequestModel, teamId);

        return ResponseEntity.status(HttpStatus.OK).body(teamResponseModel);
    }

    @DeleteMapping("/teams/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId){
        teamService.deleteTeam(teamId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/leagues/{leagueId}/teams")
    public ResponseEntity<List<TeamResponseModel>> getTeamsByLeagueId(@PathVariable Integer leagueId){
        List<TeamResponseModel> teamResponseModels = teamService.findTeamsByLeagueId(leagueId);

        return ResponseEntity.status(HttpStatus.OK).body(teamResponseModels);
    }

    @DeleteMapping("/leagues/{leagueId}/teams")
    public ResponseEntity<?> deleteTeams(@PathVariable Integer leagueId){
        teamService.deleteTeams(leagueId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
