package com.cadieux.baseballstats.dataLayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "seasons")
@NoArgsConstructor
@Setter
@Getter
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, name = "seasonid")
    private Integer seasonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="playerid", nullable = false)
    private Player player;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="battingstatsid", nullable = true)
    private BattingStats battingStats;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="pitchingstatsid", nullable = true)
    private PitchingStats pitchingStats;

    private Integer year;
    private Integer gp;
}
