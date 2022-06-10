package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@NoArgsConstructor
public class TeamDetailsResponseModel {
    private Integer teamId;
    private Integer leagueId;
    private String name;
    private String city;
}
