package com.cadieux.baseballstats.mapping.BattingStats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class BattingStatsRequestModel {
    private Integer seasonId;

    private Integer ab;
    private Integer pa;
    private Integer bb;
    private Integer singles;
    private Integer doubles;
    private Integer triples;
    private Integer hr;
    private Integer hbp;
}
