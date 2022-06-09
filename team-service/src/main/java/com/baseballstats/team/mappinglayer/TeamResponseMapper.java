package com.baseballstats.team.mappinglayer;

import com.baseballstats.team.datalayer.Team;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamResponseMapper {
    TeamResponseModel entityToResponseModel(Team entity);

    List<TeamResponseModel> entityListToResponseModelList(List<Team> entities);
}
