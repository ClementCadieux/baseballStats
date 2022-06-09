package com.cadieux.baseballstats.mapping.Player;

import com.cadieux.baseballstats.dataLayer.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PlayerRequestMapper {
    PlayerRequestModel entityToRequestModel(Player entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "playerId", ignore = true),
            @Mapping(target = "seasons", ignore = true)
    })
    Player requestModelToEntity(PlayerRequestModel model);
}
