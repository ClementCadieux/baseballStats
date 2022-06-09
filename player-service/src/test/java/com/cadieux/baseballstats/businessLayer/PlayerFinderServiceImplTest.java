package com.cadieux.baseballstats.businessLayer;

import com.cadieux.baseballstats.dataLayer.Player;
import com.cadieux.baseballstats.dataLayer.PlayerRepository;
import com.cadieux.baseballstats.exceptions.NotFoundException;
import com.cadieux.baseballstats.mapping.Player.PlayerResponseMapper;
import com.cadieux.baseballstats.mapping.Player.PlayerResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.PlayerCareerResponseMapper;
import com.cadieux.baseballstats.mapping.StatsDisplayers.PlayerCareerResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class PlayerFinderServiceImplTest {
    @Autowired
    PlayerFinderService playerFinderService;

    @MockBean
    PlayerRepository playerRepository;

    @SpyBean
    PlayerResponseMapper playerResponseMapper;

    @SpyBean
    PlayerCareerResponseMapper playerCareerResponseMapper;

    private final Integer VALID_PLAYER_ID = 1000;
    private final Integer NOT_FOUND_PLAYER_ID = 10000;
    private final Integer VALID_TEAM_ID = 1;

    private final String VALID_FIRST_NAME = "First name";
    private final String VALID_LAST_NAME = "Last name";
    private final String VALID_POSITION = "C";
    private final Integer VALID_AGE = 1;

    @Test
    void whenFindALlPlayers_thenListOfResponseModelShouldBeFound() {
        List<PlayerResponseModel> playerResponseModelList = playerFinderService.findAllPlayers();

        assertNotNull(playerResponseModelList);
        verify(playerResponseMapper, times(1)).entityListToResponseModelList(any(List.class));
    }

    @Test
    void whenValidPlayerId_PlayerResponseModelFound() {
        Player entity = createEntity();

        PlayerResponseModel playerResponseModel = playerFinderService.findPlayerByPlayerId(VALID_PLAYER_ID);

        assertEntityResponseModel(entity, playerResponseModel);
    }

    @Test
    void whenInvalidPlayerId_notFoundShouldBeThrown(){
        assertThrows(NotFoundException.class, () -> {
            playerFinderService.findPlayerByPlayerId(NOT_FOUND_PLAYER_ID.intValue());
        });

        assertThrows(NotFoundException.class, () -> {
            playerFinderService.deletePlayer(NOT_FOUND_PLAYER_ID);
        });
    }

    @Test
    void whenValidPlayer_thenCreatePlayer() {
        Player entity = createEntity();

        PlayerResponseModel playerResponseModel = playerFinderService.findPlayerByPlayerId(VALID_PLAYER_ID);

        assertEntityResponseModel(entity, playerResponseModel);
    }

    @Test
    void whenValidPlayer_thenUpdatelayer() {
        Player entity = createEntity();

        String newPosition = "P";

        entity.setPosition(newPosition);

        when(playerRepository.save(any(Player.class))).thenAnswer(i -> i.getArguments()[0]);

        PlayerResponseModel playerResponseModel = playerFinderService.findPlayerByPlayerId(VALID_PLAYER_ID);

        assertEntityResponseModel(entity, playerResponseModel);
    }

    @Test
    void whenValidPlayerId_thenDeletePlayer() {
        createEntity();

        playerFinderService.deletePlayer(VALID_PLAYER_ID);

        when(playerRepository.existsPlayerByPlayerId(VALID_PLAYER_ID)).thenReturn(false);
    }

    @Test
    void getPlayerCareer() {
        Player entity = createEntity();

        PlayerCareerResponseModel playerCareerResponseModel = playerFinderService.getPlayerCareer(VALID_PLAYER_ID);

        assertEntityCareerResponseModel(entity, playerCareerResponseModel);
    }

    @Test
    void getPlayersByTeamId() {
        Player entity = createEntity();

        List<PlayerResponseModel> playerResponseModels = playerFinderService.getPlayersByTeamId(VALID_TEAM_ID);

        for (PlayerResponseModel player:
             playerResponseModels) {
            assertNotNull(player);
            assertEquals(player.getTeamId(), entity.getTeamId());
        }

        verify(playerResponseMapper, times(1)).entityListToResponseModelList(any(List.class));
    }

    @Test
    void deletePlayersByTeam() {
        createEntity();

        playerFinderService.deletePlayersByTeam(VALID_TEAM_ID);

        when(playerRepository.existsPlayerByPlayerId(VALID_PLAYER_ID)).thenReturn(false);
    }
    private void assertEntityCareerResponseModel(Player entity, PlayerCareerResponseModel playerResponseModel) {
        assertNotNull(playerResponseModel);
        assertEquals(entity.getTeamId(), playerResponseModel.getTeamId());
        assertEquals(entity.getPlayerId(), playerResponseModel.getPlayerId());
        assertEquals(entity.getAge(), playerResponseModel.getAge());
        assertEquals(entity.getFirstName(), playerResponseModel.getFirstName());
        assertEquals(entity.getLastName(), playerResponseModel.getLastName());
        assertEquals(entity.getPosition(), playerResponseModel.getPosition());
        verify(playerCareerResponseMapper, times(1)).entityToResponseModel(any(Player.class));
    }

    private void assertEntityResponseModel(Player entity, PlayerResponseModel playerResponseModel) {
        assertNotNull(playerResponseModel);
        assertEquals(entity.getTeamId(), playerResponseModel.getTeamId());
        assertEquals(entity.getPlayerId(), playerResponseModel.getPlayerId());
        assertEquals(entity.getAge(), playerResponseModel.getAge());
        assertEquals(entity.getFirstName(), playerResponseModel.getFirstName());
        assertEquals(entity.getLastName(), playerResponseModel.getLastName());
        assertEquals(entity.getPosition(), playerResponseModel.getPosition());
        verify(playerResponseMapper, times(1)).entityToResponseModel(any(Player.class));
    }

    private Player createEntity() {
        Player entity = new Player();

        entity.setPlayerId(VALID_PLAYER_ID);
        entity.setTeamId(VALID_TEAM_ID);
        entity.setAge(VALID_AGE);
        entity.setPosition(VALID_POSITION);
        entity.setLastName(VALID_LAST_NAME);
        entity.setFirstName(VALID_FIRST_NAME);

        when(playerRepository.save(any(Player.class))).thenAnswer(i -> i.getArguments()[0]);
        when(playerRepository.existsPlayerByPlayerId(VALID_PLAYER_ID)).thenReturn(true);
        when(playerRepository.findPlayerByPlayerId(VALID_PLAYER_ID)).thenReturn(entity);

        return entity;
    }
}