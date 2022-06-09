package com.cadieux.baseballstats.presentationLayer;

import com.cadieux.baseballstats.businessLayer.SeasonFinderService;
import com.cadieux.baseballstats.mapping.Season.SeasonRequestModel;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seasons")
public class SeasonListerController {
    private SeasonFinderService seasonFinderService;

    public SeasonListerController(SeasonFinderService seasonFinderService){
        this.seasonFinderService = seasonFinderService;
    }

    @GetMapping()
    public ResponseEntity<List<SeasonResponseModel>> getAllSeasons(){
        List<SeasonResponseModel> seasons = seasonFinderService.getAllSeasons();

        return ResponseEntity.status(HttpStatus.OK).body(seasons);
    }

    @GetMapping("/{seasonId}")
    public ResponseEntity<SeasonResponseModel> getSeasonBySeasonId(@PathVariable Integer seasonId){
        SeasonResponseModel season = seasonFinderService.getSeasonBySeasonId(seasonId);

        return ResponseEntity.status(HttpStatus.OK).body(season);
    }

    @PostMapping()
    public ResponseEntity<SeasonResponseModel> createSeason(@RequestBody SeasonRequestModel seasonRequestModel){
        SeasonResponseModel season = seasonFinderService.createSeason(seasonRequestModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(season);
    }

    @PutMapping("/{seasonId}")
    public ResponseEntity<SeasonResponseModel> updateSeason(@RequestBody SeasonRequestModel seasonRequestModel, @PathVariable Integer seasonId){
        SeasonResponseModel season = seasonFinderService.updateSeason(seasonRequestModel, seasonId);

        return ResponseEntity.status(HttpStatus.OK).body(season);
    }

    @DeleteMapping("/{seasonId}")
    public ResponseEntity<?> deleteSeason(@PathVariable Integer seasonId){
        seasonFinderService.deleteSeason(seasonId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/{seasonId}/stats")
    public ResponseEntity<SeasonStatsResponseModel> getSeasonStats(@PathVariable Integer seasonId){
        SeasonStatsResponseModel season = seasonFinderService.getSeasonStats(seasonId);

        return ResponseEntity.status(HttpStatus.OK).body(season);
    }
}
