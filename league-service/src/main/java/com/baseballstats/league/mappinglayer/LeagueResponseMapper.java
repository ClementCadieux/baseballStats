package com.baseballstats.league.mappinglayer;

import com.baseballstats.league.datalayer.League;
import com.baseballstats.league.presentationlayer.LeagueController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueResponseMapper {
    LeagueResponseModel entityToResponseModel(League entity);
    List<LeagueResponseModel> entityListToResponseModelList(List<League> leagueList);

    @AfterMapping
    default void addLinks(@MappingTarget LeagueResponseModel model, League entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LeagueController.class).getLeague(entity.getLeagueId())).withSelfRel();
        model.add(selfLink);
    }
}
