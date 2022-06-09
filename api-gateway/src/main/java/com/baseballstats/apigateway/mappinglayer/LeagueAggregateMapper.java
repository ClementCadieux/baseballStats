package com.baseballstats.apigateway.mappinglayer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueAggregateMapper {
    @Mapping(target = "teamSummaryModels", ignore = true)
    LeagueAggregate responseModelToAggregate(LeagueResponseModel leagueResponseModel);

    List<LeagueAggregate> responseModelListToAggregateList(List<LeagueResponseModel> leagueResponseModels);
}
