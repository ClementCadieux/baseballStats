package com.baseballstats.team.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@NoArgsConstructor
public class TeamResponseModel extends RepresentationModel<TeamResponseModel> {
    private Integer teamId;
    private Integer leagueId;
    private String name;
    private String city;
}
