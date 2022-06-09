package com.baseballstats.league.mappinglayer;

import com.baseballstats.league.datalayer.League;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeagueResponseMapper {
    LeagueResponseModel entityToResponseModel(League entity);
    List<LeagueResponseModel> entityListToResponseModelList(List<League> leagueList);
}
