package com.baseballstats.team.mappinglayer;

import com.baseballstats.team.datalayer.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TeamRequestMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "teamId", ignore = true)
    })
    Team requestModelToEntity(TeamRequestModel teamRequestModel);
}
