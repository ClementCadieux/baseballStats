package com.baseballstats.team.datalayer;

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
class TeamRepositoryTest {
    private final Integer VALID_TEAM_ID = 1;
    private final Integer VALID_LEAGUE_ID = 1;
    private final String VALID_NAME = "name";
    private final String VALID_CITY = "city";

    @Autowired
    TeamRepository teamRepository;

    Team savedEntity;

    @BeforeEach
    void setup_db(){
        teamRepository.deleteAll();

        Team entity = new Team();

        entity.setName(VALID_NAME);
        entity.setCity(VALID_CITY);
        entity.setTeamId(VALID_TEAM_ID);
        entity.setLeagueId(VALID_LEAGUE_ID);

        savedEntity = teamRepository.save(entity);

        assertEquals(entity.getCity(), savedEntity.getCity());
        assertEquals(entity.getName(), savedEntity.getName());
        assertEquals(entity.getTeamId(), savedEntity.getTeamId());
        assertEquals(entity.getLeagueId(), savedEntity.getLeagueId());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenValidTeamId_thenTeamShouldBeFound() {
        Team entity = teamRepository.findTeamByTeamId(VALID_TEAM_ID);

        assertEquals(entity.getCity(), savedEntity.getCity());
        assertEquals(entity.getName(), savedEntity.getName());
        assertEquals(entity.getTeamId(), savedEntity.getTeamId());
        assertEquals(entity.getLeagueId(), savedEntity.getLeagueId());
    }

    @Test
    void whenSaveWithDuplicateTeamId_thenDataIntegrityViolationException(){
        Team entity = new Team();

        entity.setName(VALID_NAME);
        entity.setCity(VALID_CITY);
        entity.setTeamId(VALID_TEAM_ID);
        entity.setLeagueId(VALID_LEAGUE_ID);

        assertThrows(DataIntegrityViolationException.class,
                () -> {
                    teamRepository.save(entity);
                });
    }

    @Test
    void whenTeamExists_existsTeamShouldBeTrue() {
        assertEquals(teamRepository.existsTeamByTeamId(VALID_TEAM_ID), true);
    }

    @Test
    void whenValidLeagueId_thenTeamsShouldBeFound() {
        List<Team> entities = teamRepository.findTeamsByLeagueId(VALID_LEAGUE_ID);

        for(Team entity : entities){
            assertEquals(entity.getLeagueId(), savedEntity.getLeagueId());
        }
    }

    @Test
    void whenTeamDeleted_thenExistsTeamShouldBeFalse() {
        teamRepository.deleteTeamByTeamId(VALID_TEAM_ID);

        assertEquals(teamRepository.existsTeamByTeamId(VALID_TEAM_ID), false);
    }

    @Test
    void whenTeamsDelete_thenExistsTeamShouldBeFalse() {
        teamRepository.deleteTeamsByLeagueId(VALID_LEAGUE_ID);

        assertEquals(teamRepository.existsTeamByTeamId(VALID_TEAM_ID), false);
    }
}