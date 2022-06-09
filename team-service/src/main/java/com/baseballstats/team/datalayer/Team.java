package com.baseballstats.team.datalayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "teams")
@NoArgsConstructor
@Setter
@Getter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, name = "teamid")
    private Integer teamId;

    @Column(name = "leagueid")
    private Integer leagueId;

    private String name;
    private String city;
}
