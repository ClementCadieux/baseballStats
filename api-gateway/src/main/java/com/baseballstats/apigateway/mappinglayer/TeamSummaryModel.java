package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
@NoArgsConstructor
public class TeamSummaryModel extends RepresentationModel<TeamSummaryModel> {
    private String name;
    private String city;
}
