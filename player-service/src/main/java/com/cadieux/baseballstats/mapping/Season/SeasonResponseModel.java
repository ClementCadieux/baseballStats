package com.cadieux.baseballstats.mapping.Season;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Setter
@Getter
public class SeasonResponseModel extends RepresentationModel<SeasonResponseModel> {
    private Integer seasonId;
    private Integer playerId;
    private Integer year;
    private Integer gp;
}
