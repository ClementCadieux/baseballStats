package com.baseballstats.apigateway.mappinglayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PlayerRequestModel {
    private Integer teamId;
    private String firstName;
    private String lastName;
    private String position;
    private Integer age;
}
