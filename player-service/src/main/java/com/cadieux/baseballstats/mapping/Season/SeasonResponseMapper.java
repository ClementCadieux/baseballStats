package com.cadieux.baseballstats.mapping.Season;

import com.cadieux.baseballstats.dataLayer.Season;
import com.cadieux.baseballstats.presentationLayer.PlayerListerController;
import com.cadieux.baseballstats.presentationLayer.SeasonListerController;
import org.mapstruct.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeasonResponseMapper {
    public Season responseModelToEntity(SeasonResponseModel responseModel);

    @Mapping(expression = "java(entity.getPlayer().getPlayerId())", target = "playerId")
    public SeasonResponseModel entityToResponseModel(Season entity);

    public List<SeasonResponseModel> entityListToResponseModelList(List<Season> entities);

    @AfterMapping
    default void addLinks(@MappingTarget SeasonResponseModel model, Season entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SeasonListerController.class).getSeasonBySeasonId(entity.getSeasonId())).withSelfRel();
        Link playerLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerListerController.class).getPlayerByPlayerId(entity.getPlayer().getPlayerId())).withRel("player");
        model.add(selfLink);
        model.add(playerLink);
    }
}
