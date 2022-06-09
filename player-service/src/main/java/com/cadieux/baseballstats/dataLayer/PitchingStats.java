package com.cadieux.baseballstats.dataLayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pitchingstats")
@NoArgsConstructor
@Setter
@Getter
public class PitchingStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pitchingstatsid", unique = true)
    private Integer pitchingId;

    private Integer ip;
    private Integer gs;
    private Integer sv;
    private Integer h;
    private Integer er;
    private Integer k;
    private Integer bb;
    private Integer w;
    private Integer l;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pitchingStats")
    private Season season;

}
