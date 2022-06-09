package com.cadieux.baseballstats.mapping.StatsDisplayers;

import com.cadieux.baseballstats.dataLayer.Player;
import com.cadieux.baseballstats.presentationLayer.PlayerListerController;
import org.mapstruct.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerCareerResponseMapper {
    @Mappings({
            @Mapping(target = "seasons", ignore = true),

            @Mapping(target = "gp", ignore = true),

            @Mapping(target = "ab", ignore = true),
            @Mapping(target = "pa", ignore = true),
            @Mapping(target = "bb", ignore = true),
            @Mapping(target = "singles", ignore = true),
            @Mapping(target = "doubles", ignore = true),
            @Mapping(target = "triples", ignore = true),
            @Mapping(target = "hr", ignore = true),
            @Mapping(target = "hbp", ignore = true),

            @Mapping(target = "b_h", ignore = true),
            @Mapping(target = "avg", ignore = true),
            @Mapping(target = "obp", ignore = true),

            @Mapping(target = "ip", ignore = true),
            @Mapping(target = "gs", ignore = true),
            @Mapping(target = "sv", ignore = true),
            @Mapping(target = "h", ignore = true),
            @Mapping(target = "er", ignore = true),
            @Mapping(target = "k", ignore = true),
            @Mapping(target = "p_bb", ignore = true),
            @Mapping(target = "w", ignore = true),
            @Mapping(target = "l", ignore = true),

            @Mapping(target = "era", ignore = true),
            @Mapping(target = "WPercentage", ignore = true),
    })
    public PlayerCareerResponseModel entityToResponseModel(Player entity);

    public List<PlayerCareerResponseModel> entityListToResponseModelList(List<Player> entities);

    @AfterMapping
    default void addLinks(@MappingTarget PlayerCareerResponseModel model, Player entity){
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerListerController.class).getPlayerCareer(entity.getPlayerId())).withSelfRel();
        Link seasonsLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PlayerListerController.class).getPlayerSeasons(entity.getPlayerId())).withRel("Seasons");
        model.add(selfLink);
        model.add(seasonsLink);
    }
}
