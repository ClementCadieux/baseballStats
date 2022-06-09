package com.cadieux.baseballstats.dataLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class PlayerRepositoryTest {

    private final Integer VALID_PLAYER_ID = 101;
    private final Integer VALID_TEAM_ID = 1;
    private final String VALID_FIRST_NAME = "First name";
    private final String VALID_LAST_NAME = "Last name";
    private final String VALID_POSITION = "C";
    private final Integer VALID_AGE = 1;

    Player savedEntity;

    @Autowired
    private PlayerRepository playerRepository;

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

        savedEntity = playerRepository.save(entity);

        assertEquals(entity.getPlayerId(), savedEntity.getPlayerId());
        assertEquals(entity.getTeamId(), savedEntity.getTeamId());
        assertEquals(entity.getFirstName(), savedEntity.getFirstName());
        assertEquals(entity.getLastName(), savedEntity.getLastName());
        assertEquals(entity.getPosition(), savedEntity.getPosition());
        assertEquals(entity.getAge(), savedEntity.getAge());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenValidPlayerId_thenPlayerShouldBeFound() {
        Player foundEntity = playerRepository.findPlayerByPlayerId(VALID_PLAYER_ID);

        assertEquals(foundEntity.getPlayerId(), savedEntity.getPlayerId());
        assertEquals(foundEntity.getTeamId(), savedEntity.getTeamId());
        assertEquals(foundEntity.getFirstName(), savedEntity.getFirstName());
        assertEquals(foundEntity.getLastName(), savedEntity.getLastName());
        assertEquals(foundEntity.getPosition(), savedEntity.getPosition());
        assertEquals(foundEntity.getAge(), savedEntity.getAge());
    }

    @Test
    void whenSaveWithDuplicatePlayerId_thenDataIntegrityExceptionThrown(){
        Player entity = new Player();
        entity.setPlayerId(VALID_PLAYER_ID);
        entity.setTeamId(VALID_TEAM_ID);
        entity.setFirstName(VALID_FIRST_NAME);
        entity.setLastName(VALID_LAST_NAME);
        entity.setPosition(VALID_POSITION);
        entity.setAge(VALID_AGE);

        assertThrows(DataIntegrityViolationException.class, () -> {
            playerRepository.save(entity);
        });
    }

    @Test
    void whenValidPlayerId_thenExistsPlayerShouldBeTrue() {
        assertEquals(playerRepository.existsPlayerByPlayerId(VALID_PLAYER_ID), true);
    }

    @Test
    void whenDeleteByPlayerId_thenExistsPlayerShouldBeFalse() {
        playerRepository.deletePlayerByPlayerId(VALID_PLAYER_ID);

        assertEquals(playerRepository.existsPlayerByPlayerId(VALID_PLAYER_ID), false);
    }

    @Test
    void whenValidTeamId_thenListOfPlayerShouldBeFound() {
        List<Player> players = playerRepository.findPlayersByTeamId(VALID_TEAM_ID);

        assertEquals(players.size() >= 1, true);
    }

    @Test
    void whenPlayersDeleteByTeamId_thenPlayersByTeamIdShouldBeEmpty() {
        playerRepository.deletePlayersByTeamId(VALID_TEAM_ID);

        List<Player> players = playerRepository.findPlayersByTeamId(VALID_TEAM_ID);

        assertEquals(players.size() >= 1, false);
    }
}