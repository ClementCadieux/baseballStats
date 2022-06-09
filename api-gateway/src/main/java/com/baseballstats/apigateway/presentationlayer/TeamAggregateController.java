package com.baseballstats.apigateway.presentationlayer;

import com.baseballstats.apigateway.businesslayer.TeamAggregateService;
import com.baseballstats.apigateway.exceptions.InvalidInputException;
import com.baseballstats.apigateway.exceptions.NotFoundException;
import com.baseballstats.apigateway.mappinglayer.TeamAggregate;
import com.baseballstats.apigateway.mappinglayer.TeamAggregateRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamAggregateController {
    private TeamAggregateService teamAggregateService;

    public TeamAggregateController(TeamAggregateService teamAggregateService){
        this.teamAggregateService = teamAggregateService;
    }

    @GetMapping(
        value = "/{teamId}",
        produces = "application/json"
    )
    public ResponseEntity<TeamAggregate> getTeamByTeamId(@PathVariable Integer teamId){
        validateTeamId(teamId);
        TeamAggregate teamAggregate = teamAggregateService.getTeamByTeamId(teamId);

        return ResponseEntity.status(HttpStatus.OK).body(teamAggregate);
    }

    @PostMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseEntity<TeamAggregate> createTeam(@RequestBody TeamAggregateRequestModel teamAggregateRequestModel){
        TeamAggregate teamAggregate = teamAggregateService.createTeam(teamAggregateRequestModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(teamAggregate);
    }

    @PutMapping(
            value = "/{teamId}",
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseEntity<TeamAggregate> updateTeam(@RequestBody TeamAggregateRequestModel teamAggregateRequestModel, @PathVariable Integer teamId){
        validateTeamId(teamId);

        TeamAggregate teamAggregate = teamAggregateService.updateTeam(teamAggregateRequestModel, teamId);

        return ResponseEntity.status(HttpStatus.OK).body(teamAggregate);
    }

    @DeleteMapping(
            value = "/{teamId}"
    )
    public ResponseEntity<?> deleteTeam(@PathVariable Integer teamId){
        validateTeamId(teamId);

        teamAggregateService.deleteTeam(teamId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    private void validateTeamId(Integer teamId){
        if(teamId < 0) throw new InvalidInputException("Invalid teamId given: " + teamId);
    }
}
