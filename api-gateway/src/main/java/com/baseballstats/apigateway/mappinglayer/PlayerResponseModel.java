package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlayerResponseModel {
    private Integer playerId;
    private Integer teamId;
    private String firstName;
    private String lastName;
    private String position;
    private Integer age;
}
