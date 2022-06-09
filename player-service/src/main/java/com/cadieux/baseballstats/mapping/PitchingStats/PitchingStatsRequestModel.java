package com.cadieux.baseballstats.mapping.PitchingStats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PitchingStatsRequestModel {
    private Integer ip;
    private Integer gs;
    private Integer sv;
    private Integer h;
    private Integer er;
    private Integer k;
    private Integer bb;
    private Integer w;
    private Integer l;
}
