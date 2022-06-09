package com.cadieux.baseballstats.mapping.StatsDisplayers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Setter
@Getter
public class SeasonStatsResponseModel extends RepresentationModel<SeasonStatsResponseModel> {
    private Integer seasonId;
    private Integer playerId;
    private Integer year;
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
