package com.cadieux.baseballstats.businessLayer;

import com.cadieux.baseballstats.mapping.Season.SeasonRequestModel;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseModel;

import java.util.List;

public interface SeasonFinderService {
    public List<SeasonResponseModel> getAllSeasons();

    public SeasonResponseModel getSeasonBySeasonId(Integer seasonId);

    public SeasonResponseModel createSeason(SeasonRequestModel requestModel);

    public SeasonResponseModel updateSeason(SeasonRequestModel requestModel, Integer seasonId);

    public void deleteSeason(Integer seasonId);

    public SeasonStatsResponseModel getSeasonStats(Integer seasonId);
}
