package com.cadieux.baseballstats.mapping.Player;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Setter
@Getter
public class PlayerResponseModel extends RepresentationModel<PlayerResponseModel> {
    private Integer playerId;
    private Integer teamId;
    private String firstName;
    private String lastName;
    private String position;
    private Integer age;
}
