package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class LeagueAggregate {
    private Integer leagueId;

    private String name;
    private String category;

    private List<TeamSummaryModel> teamSummaryModels;
}
