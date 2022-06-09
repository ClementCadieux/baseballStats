package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TeamAggregateRequestModel {
    private Integer leagueId;
    private String name;
    private String city;

    private List<PlayerSummaryModel> players;
}
