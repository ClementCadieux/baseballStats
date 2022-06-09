package com.cadieux.baseballstats.dataLayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "battingstats")
@NoArgsConstructor
@Setter
@Getter
public class BattingStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "battingstatsid", unique = true)
    private Integer battingId;

    private Integer ab;
    private Integer pa;
    private Integer bb;
    private Integer singles;
    private Integer doubles;
    private Integer triples;
    private Integer hr;
    private Integer hbp;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "battingStats")
    private Season season;
}
