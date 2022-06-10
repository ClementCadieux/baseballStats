package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class TeamAggregate extends RepresentationModel<TeamAggregate> {
    private Integer leagueId;
    private Integer teamId;
    private String name;
    private String city;

    private List<PlayerSummaryModel> players;
}
