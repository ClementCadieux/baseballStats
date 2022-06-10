package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class LeagueAggregate extends RepresentationModel<LeagueAggregate> {
    private Integer leagueId;

    private String name;
    private String category;

    private List<TeamSummaryModel> teamSummaryModels;
}
