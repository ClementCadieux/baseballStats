package com.baseballstats.league.datalayer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leagues")
@NoArgsConstructor
@Setter
@Getter
public class League {

    @Id
    private String id;

    @Indexed(unique = true)
    private Integer leagueId;

    private String name;
    private String category;
}
