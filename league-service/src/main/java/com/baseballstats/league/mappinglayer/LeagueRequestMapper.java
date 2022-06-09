package com.baseballstats.league.mappinglayer;

import com.baseballstats.league.datalayer.League;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LeagueRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "leagueId", ignore = true)
    })
    League requestModelToEntity(LeagueRequestModel leagueRequestModel);
}
