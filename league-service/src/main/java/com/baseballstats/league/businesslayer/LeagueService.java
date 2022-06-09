package com.baseballstats.league.businesslayer;

import com.baseballstats.league.mappinglayer.LeagueRequestModel;
import com.baseballstats.league.mappinglayer.LeagueResponseModel;

public interface LeagueService {
    LeagueResponseModel getLeagueByLeagueId(Integer leagueId);
    LeagueResponseModel createLeague(LeagueRequestModel leagueRequestModel);
    LeagueResponseModel updateLeague(LeagueRequestModel leagueRequestModel, Integer leagueId);
    void deleteLeague(Integer leagueId);
}
