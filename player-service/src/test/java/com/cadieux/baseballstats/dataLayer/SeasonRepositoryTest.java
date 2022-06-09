package com.cadieux.baseballstats.dataLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class SeasonRepositoryTest {
    private final Integer VALID_SEASON_ID = 1;
    private final Integer VALID_YEAR = 100;
    private final Integer VALID_GP = 1;

    private final Integer VALID_PLAYER_ID = 101;
    private final Integer VALID_TEAM_ID = 1;
    private final String VALID_FIRST_NAME = "First name";
    private final String VALID_LAST_NAME = "Last name";
    private final String VALID_POSITION = "C";
    private final Integer VALID_AGE = 1;

    Season savedEntity;
    Player savedPlayer;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    PlayerRepository playerRepository;

    @BeforeEach
    void setup_db(){
        playerRepository.deleteAll();

        Player entity = new Player();
        entity.setPlayerId(VALID_PLAYER_ID);
        entity.setTeamId(VALID_TEAM_ID);
        entity.setFirstName(VALID_FIRST_NAME);
        entity.setLastName(VALID_LAST_NAME);
        entity.setPosition(VALID_POSITION);
        entity.setAge(VALID_AGE);

        savedPlayer = playerRepository.save(entity);

        seasonRepository.deleteAll();

        Season season = new Season();

        season.setSeasonId(VALID_SEASON_ID);
        season.setYear(VALID_YEAR);
        season.setGp(VALID_GP);
        season.setPlayer(savedPlayer);

        savedEntity = seasonRepository.save(season);

        assertEquals(season.getSeasonId(), savedEntity.getSeasonId());
        assertEquals(season.getYear(), savedEntity.getYear());
        assertEquals(season.getGp(), savedEntity.getGp());
        assertEquals(season.getPlayer(), savedEntity.getPlayer());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenValidSeasonId_getSeasonShouldReturn() {
        Season season = seasonRepository.findSeasonBySeasonId(VALID_SEASON_ID);

        assertEquals(season.getSeasonId(), savedEntity.getSeasonId());
        assertEquals(season.getYear(), savedEntity.getYear());
        assertEquals(season.getGp(), savedEntity.getGp());
        assertEquals(season.getPlayer(), savedEntity.getPlayer());
    }

    @Test
    void whenSavingWithDuplicatedSeasonId_thenDataIntegrityExceptionThrown(){
        Season season = new Season();

        season.setSeasonId(VALID_SEASON_ID);
        season.setYear(VALID_YEAR);
        season.setGp(VALID_GP);
        season.setPlayer(savedPlayer);

        assertThrows(DataIntegrityViolationException.class, () -> {
            seasonRepository.save(season);
        });
    }

    @Test
    void whenValidSeasonId_thenExistsSeasonBySeasonIdShouldBeTrue() {
        assertEquals(seasonRepository.existsSeasonBySeasonId(VALID_SEASON_ID), true);
    }

    @Test
    void whenDeleteSeasonId_thenExistsSeasonBySeasonIdShouldBeFalse() {
        seasonRepository.deleteSeasonBySeasonId(VALID_SEASON_ID);

        assertEquals(seasonRepository.existsSeasonBySeasonId(VALID_SEASON_ID), false);
    }

    @Test
    void whenValidPlayer_findSeasonByPlayerShouldReturnSeason() {
        Season season = seasonRepository.findSeasonsByPlayer(savedPlayer).get(0);

        assertEquals(season.getSeasonId(), savedEntity.getSeasonId());
        assertEquals(season.getYear(), savedEntity.getYear());
        assertEquals(season.getGp(), savedEntity.getGp());
        assertEquals(season.getPlayer(), savedEntity.getPlayer());
    }
}