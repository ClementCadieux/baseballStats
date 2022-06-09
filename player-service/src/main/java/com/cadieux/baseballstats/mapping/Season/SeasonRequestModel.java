package com.cadieux.baseballstats.mapping.Season;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class SeasonRequestModel {
    private Integer playerId;
    //if doesn't exists, put -1
    private Integer battingStatsId;
    //if doesn't exists, put -1
    private Integer pitchingStatsId;
    private Integer year;
    private Integer gp;

    private Integer ab;
    private Integer pa;
    private Integer bb;
    private Integer singles;
    private Integer doubles;
    private Integer triples;
    private Integer hr;
    private Integer hbp;

    private Integer ip;
    private Integer gs;
    private Integer sv;
    private Integer h;
    private Integer er;
    private Integer k;
    private Integer p_bb;
    private Integer w;
    private Integer l;
}
