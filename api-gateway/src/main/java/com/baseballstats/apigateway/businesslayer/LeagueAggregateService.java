package com.baseballstats.apigateway.businesslayer;

import com.baseballstats.apigateway.mappinglayer.LeagueAggregate;
import com.baseballstats.apigateway.mappinglayer.LeagueAggregateRequestModel;
import com.baseballstats.apigateway.mappinglayer.LeagueRequestModel;

public interface LeagueAggregateService {
    LeagueAggregate getLeagueById(Integer leagueId);
    LeagueAggregate createLeague(LeagueAggregateRequestModel leagueAggregateRequestModel);
    LeagueAggregate updateLeague(LeagueRequestModel leagueRequestModel, Integer leagueId);
    void deleteLeague(Integer leagueId);
}
