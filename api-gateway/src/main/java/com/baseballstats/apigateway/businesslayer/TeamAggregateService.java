package com.baseballstats.apigateway.businesslayer;

import com.baseballstats.apigateway.mappinglayer.TeamAggregate;
import com.baseballstats.apigateway.mappinglayer.TeamAggregateRequestModel;

public interface TeamAggregateService {
    TeamAggregate getTeamByTeamId(Integer teamId);
    TeamAggregate createTeam(TeamAggregateRequestModel teamAggregateRequestModel);
    TeamAggregate updateTeam(TeamAggregateRequestModel teamAggregateRequestModel, Integer teamId);
    void deleteTeam(Integer teamId);
}
