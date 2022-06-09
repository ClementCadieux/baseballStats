package com.cadieux.baseballstats.mapping.PitchingStats;

import com.cadieux.baseballstats.dataLayer.PitchingStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PitchingStatsRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "pitchingId", ignore = true),
            @Mapping(target = "season", ignore = true)
    })
    public PitchingStats requestModelToEntity(PitchingStatsRequestModel requestModel);
}
