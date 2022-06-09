package com.baseballstats.apigateway.presentationlayer;

import com.baseballstats.apigateway.businesslayer.LeagueAggregateService;
import com.baseballstats.apigateway.exceptions.InvalidInputException;
import com.baseballstats.apigateway.mappinglayer.LeagueAggregate;
import com.baseballstats.apigateway.mappinglayer.LeagueAggregateRequestModel;
import com.baseballstats.apigateway.mappinglayer.LeagueRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leagues")
public class LeagueAggregateController {
    private LeagueAggregateService leagueAggregateService;

    public LeagueAggregateController(LeagueAggregateService leagueAggregateService) {
        this.leagueAggregateService = leagueAggregateService;
    }

    @GetMapping(
            value = "/{leagueId}",
            produces = "application/json"
    )
    public ResponseEntity<LeagueAggregate> getLeague(@PathVariable Integer leagueId){
        validateLeagueId(leagueId);
        LeagueAggregate leagueAggregate = leagueAggregateService.getLeagueById(leagueId);

        return ResponseEntity.status(HttpStatus.OK).body(leagueAggregate);
    }

    @PostMapping(
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseEntity<LeagueAggregate> createLeague(@RequestBody LeagueAggregateRequestModel leagueAggregateRequestModel) {
        LeagueAggregate leagueAggregate = leagueAggregateService.createLeague(leagueAggregateRequestModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(leagueAggregate);
    }

    @PutMapping(
            value = "/{leagueId}",
            produces = "application/json",
            consumes = "application/json"
    )
    public ResponseEntity<LeagueAggregate> updateLeague(@RequestBody LeagueRequestModel leagueRequestModel, @PathVariable Integer leagueId) {
        validateLeagueId(leagueId);
        LeagueAggregate leagueAggregate = leagueAggregateService.updateLeague(leagueRequestModel, leagueId);

        return ResponseEntity.status(HttpStatus.OK).body(leagueAggregate);
    }

    @DeleteMapping(value = "/{leagueId}")
    public ResponseEntity<?> deleteLeague(@PathVariable Integer leagueId){
        validateLeagueId(leagueId);
        leagueAggregateService.deleteLeague(leagueId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    private void validateLeagueId(Integer leagueId){
        if(leagueId < 0) throw new InvalidInputException("Invalid leagueId given: " + leagueId);
    }
}
