package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class TeamDetailsResponseModel {
    private Integer teamId;
    private Integer leagueId;
    private String name;
    private String city;
}
