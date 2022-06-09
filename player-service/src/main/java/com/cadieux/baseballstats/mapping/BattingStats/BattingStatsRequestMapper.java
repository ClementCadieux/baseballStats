package com.cadieux.baseballstats.mapping.BattingStats;

import com.cadieux.baseballstats.dataLayer.BattingStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BattingStatsRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "battingId", ignore = true),
            @Mapping(target = "season", ignore = true)
    })
    public BattingStats requestModelToEntity(BattingStatsRequestModel requestModel);
}
