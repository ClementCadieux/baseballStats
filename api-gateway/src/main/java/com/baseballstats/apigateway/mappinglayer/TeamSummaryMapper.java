package com.baseballstats.apigateway.mappinglayer;

import com.baseballstats.apigateway.presentationlayer.TeamAggregateController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamSummaryMapper {
    TeamSummaryModel responseModelToSummaryModel(TeamDetailsResponseModel responseModel);
    List<TeamSummaryModel> responseModelListToSummaryModelList(List<TeamDetailsResponseModel> responseModels);

    @AfterMapping
    default void addLinks(@MappingTarget TeamSummaryModel model, TeamDetailsResponseModel entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TeamAggregateController.class).getTeamByTeamId(entity.getTeamId())).withSelfRel();
        model.add(selfLink);
    }
}
