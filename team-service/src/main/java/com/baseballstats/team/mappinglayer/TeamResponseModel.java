package com.baseballstats.team.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TeamResponseModel {
    private Integer teamId;
    private Integer leagueId;
    private String name;
    private String city;
}
