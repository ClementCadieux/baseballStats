package com.baseballstats.apigateway.mappinglayer;

import com.baseballstats.apigateway.presentationlayer.LeagueAggregateController;
import com.baseballstats.apigateway.presentationlayer.TeamAggregateController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueAggregateMapper {
    @Mapping(target = "teamSummaryModels", ignore = true)
    LeagueAggregate responseModelToAggregate(LeagueResponseModel leagueResponseModel);

    List<LeagueAggregate> responseModelListToAggregateList(List<LeagueResponseModel> leagueResponseModels);

    @AfterMapping
    default void addLinks(@MappingTarget LeagueAggregate model, LeagueResponseModel entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeagueAggregateController.class).getLeague(entity.getLeagueId())).withSelfRel();
        model.add(selfLink);
    }
}
