package com.cadieux.baseballstats.mapping.Season;

import com.cadieux.baseballstats.dataLayer.Season;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SeasonRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "seasonId", ignore = true),
            @Mapping(target = "player", ignore = true),
            @Mapping(target = "pitchingStats", ignore = true),
            @Mapping(target = "battingStats", ignore = true)
    })
    public Season requestModelToEntity(SeasonRequestModel requestModel);

    public SeasonRequestModel entityToRequestModel(Season entity);
}
