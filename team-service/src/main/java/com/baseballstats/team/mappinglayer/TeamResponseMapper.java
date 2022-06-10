package com.baseballstats.team.mappinglayer;

import com.baseballstats.team.datalayer.Team;
import com.baseballstats.team.presentationlayer.TeamController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamResponseMapper {
    TeamResponseModel entityToResponseModel(Team entity);

    List<TeamResponseModel> entityListToResponseModelList(List<Team> entities);

    @AfterMapping
    default void addLinks(@MappingTarget TeamResponseModel model, Team entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TeamController.class).getTeamByTeamId(entity.getTeamId())).withSelfRel();
        model.add(selfLink);
    }
}
