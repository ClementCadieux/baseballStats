package com.cadieux.baseballstats.mapping.StatsDisplayers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class PlayerCareerResponseModel extends RepresentationModel<PlayerCareerResponseModel> {
    private Integer playerId;
    private Integer teamId;
    private String firstName;
    private String lastName;
    private String position;
    private Integer age;

    List<SeasonStatsResponseModel> seasons;

    //career totals/averages. These are done manually in the playerFinderService based on seasons
    private Integer gp;
    //In db stats
    private Integer ab;
    private Integer pa;
    private Integer bb;
    private Integer singles;
    private Integer doubles;
    private Integer triples;
    private Integer hr;
    private Integer hbp;

    //Derived stats
    private Integer b_h;
    private double avg;
    private double obp;

    //In db stats
    private Integer ip;
    private Integer gs;
    private Integer sv;
    private Integer h;
    private Integer er;
    private Integer k;
    private Integer p_bb;
    private Integer w;
    private Integer l;

    //Derived stats
    private double era;
    private double wPercentage;
}
