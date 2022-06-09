package com.baseballstats.apigateway.mappinglayer;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamSummaryMapper {
    TeamSummaryModel responseModelToSummaryModel(TeamDetailsResponseModel responseModel);
    List<TeamSummaryModel> responseModelListToSummaryModelList(List<TeamDetailsResponseModel> responseModels);
}
