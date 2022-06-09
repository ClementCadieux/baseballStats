package com.cadieux.baseballstats.mapping.Player;

import com.cadieux.baseballstats.dataLayer.Player;
import com.cadieux.baseballstats.presentationLayer.PlayerListerController;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerResponseMapper {
    PlayerResponseModel entityToResponseModel(Player entity);

    @Mapping(target = "id", ignore = true)
    Player responseModelToEntity(PlayerResponseModel responseModel);

    List<PlayerResponseModel> entityListToResponseModelList(List<Player> players);

    @AfterMapping
    default void addLinks(@MappingTarget PlayerResponseModel model, Player entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerListerController.class).getPlayerByPlayerId(entity.getPlayerId())).withSelfRel();
        model.add(selfLink);
    }
}
