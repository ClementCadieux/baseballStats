package com.baseballstats.league.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class LeagueRepositoryTest {

    @Autowired
    LeagueRepository leagueRepository;

    League savedEntity;

    private final Integer VALID_LEAGUE_ID = 1;

    private final String VALID_NAME = "Name";
    private final String VALID_CATEGORY = "Category";

    @BeforeEach
    void setup_db(){
        leagueRepository.deleteAll();

        League entity = new League();
        entity.setName(VALID_NAME);
        entity.setCategory(VALID_CATEGORY);
        entity.setLeagueId(VALID_LEAGUE_ID);

        savedEntity = leagueRepository.save(entity);

        assertEquals(entity.getLeagueId(), savedEntity.getLeagueId());
        assertEquals(entity.getName(), savedEntity.getName());
        assertEquals(entity.getCategory(), savedEntity.getCategory());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenValidLeagueId_thenFindLeague() {
        League entity = leagueRepository.findLeagueByLeagueId(VALID_LEAGUE_ID);

        assertEquals(entity.getLeagueId(), savedEntity.getLeagueId());
        assertEquals(entity.getName(), savedEntity.getName());
        assertEquals(entity.getCategory(), savedEntity.getCategory());
    }

    @Test
    void whenValidLeagueId_existsLeagueShouldBeTrue() {
        assertEquals(leagueRepository.existsLeagueByLeagueId(VALID_LEAGUE_ID), true);
    }

    @Test
    void deleteLeagueByLeagueId() {
        leagueRepository.deleteLeagueByLeagueId(VALID_LEAGUE_ID);

        assertEquals(leagueRepository.existsLeagueByLeagueId(VALID_LEAGUE_ID), false);
    }
}