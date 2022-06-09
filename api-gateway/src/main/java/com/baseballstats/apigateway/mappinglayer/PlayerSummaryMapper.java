package com.baseballstats.apigateway.mappinglayer;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerSummaryMapper {
    PlayerSummaryModel responseModelToSummaryModel(PlayerResponseModel responseModel);

    List<PlayerSummaryModel> responseModelListToSummaryModelList(List<PlayerResponseModel> responseModels);
}
