package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Setter
@Getter
public class LeagueResponseModel {
    private Integer leagueId;

    private String name;
    private String category;
}
