package com.baseballstats.apigateway.mappinglayer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamAggregateMapper {
    @Mapping(target = "players", ignore = true)
    TeamAggregate responseModelToAggregate(TeamDetailsResponseModel responseModel);

    List<TeamAggregate> responseModelListToAggregateList(List<TeamDetailsResponseModel> responseModels);
}
