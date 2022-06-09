package com.baseballstats.league.presentationlayer;

import com.baseballstats.league.businesslayer.LeagueService;
import com.baseballstats.league.mappinglayer.LeagueRequestModel;
import com.baseballstats.league.mappinglayer.LeagueResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {
    private LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("/{leagueId}")
    public ResponseEntity<LeagueResponseModel> getLeague(@PathVariable Integer leagueId){
        LeagueResponseModel leagueResponseModel = leagueService.getLeagueByLeagueId(leagueId);

        return ResponseEntity.status(HttpStatus.OK).body(leagueResponseModel);
    }

    @PostMapping()
    public ResponseEntity<LeagueResponseModel> createLeague(@RequestBody LeagueRequestModel leagueRequestModel){
        LeagueResponseModel leagueResponseModel = leagueService.createLeague(leagueRequestModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(leagueResponseModel);
    }

    @PutMapping("/{leagueId}")
    public ResponseEntity<LeagueResponseModel> updateLeague(@RequestBody LeagueRequestModel leagueRequestModel, @PathVariable Integer leagueId){
        LeagueResponseModel leagueResponseModel = leagueService.updateLeague(leagueRequestModel, leagueId);

        return ResponseEntity.status(HttpStatus.OK).body(leagueResponseModel);
    }

    @DeleteMapping("/{leagueId}")
    public ResponseEntity<?> deleteLeague(@PathVariable Integer leagueId){
        leagueService.deleteLeague(leagueId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
