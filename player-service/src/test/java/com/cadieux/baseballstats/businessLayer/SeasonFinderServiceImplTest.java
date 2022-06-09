package com.cadieux.baseballstats.businessLayer;

import com.cadieux.baseballstats.dataLayer.Player;
import com.cadieux.baseballstats.dataLayer.PlayerRepository;
import com.cadieux.baseballstats.dataLayer.Season;
import com.cadieux.baseballstats.dataLayer.SeasonRepository;
import com.cadieux.baseballstats.exceptions.NotFoundException;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseMapper;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseMapper;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class SeasonFinderServiceImplTest {
    @Autowired
    SeasonFinderService seasonFinderService;

    @Autowired
    PlayerRepository playerRepository;

    @MockBean
    SeasonRepository seasonRepository;

    @SpyBean
    SeasonResponseMapper seasonResponseMapper;

    @SpyBean
    SeasonStatsResponseMapper seasonStatsResponseMapper;

    private final Integer VALID_SEASON_ID = 100;
    private final Integer INVALID_SEASON_ID = 101;
    private final Integer VALID_YEAR = 100;
    private final Integer VALID_GP = 1;

    private final Integer VALID_PLAYER_ID = 200;
    private final Integer VALID_TEAM_ID = 1;

    private final String VALID_FIRST_NAME = "First name";
    private final String VALID_LAST_NAME = "Last name";
    private final String VALID_POSITION = "C";
    private final Integer VALID_AGE = 1;

    @AfterEach
    void tear_down(){
        playerRepository.deleteAll();
    }


    @Test
    void whenSeasonExist_getAllSeasonsReturnsListOfResponseModels() {
        List<SeasonResponseModel> seasonResponseModelList = seasonFinderService.getAllSeasons();

        assertNotNull(seasonResponseModelList);
        verify(seasonResponseMapper, times(1)).entityListToResponseModelList(any(List.class));
    }

    @Test
    void whenValidSeasonId_thenGetSeason() {
        Season entity = createEntity();

        SeasonResponseModel seasonResponseModel = seasonFinderService.getSeasonBySeasonId(VALID_SEASON_ID);

        assertEntityResponseModel(entity, seasonResponseModel);
    }

    @Test
    void whenInvalidSeasonId_throwNotFound(){
        assertThrows(NotFoundException.class, () -> {
            seasonFinderService.getSeasonBySeasonId(INVALID_SEASON_ID);
        });

        assertThrows(NotFoundException.class, () -> {
            seasonFinderService.deleteSeason(INVALID_SEASON_ID);
        });
    }

    @Test
    void whenSeasonCreated_getNewSeason() {
        Season entity = createEntity();

        SeasonResponseModel seasonResponseModel = seasonFinderService.getSeasonBySeasonId(VALID_SEASON_ID);

        assertEntityResponseModel(entity, seasonResponseModel);
    }

    @Test
    void whenSeasonUpdated_getNewSeason() {
        Season entity = createEntity();

        entity.setYear(2000);

        when(seasonRepository.save(any(Season.class))).thenAnswer(i -> i.getArguments()[0]);

        SeasonResponseModel seasonResponseModel = seasonFinderService.getSeasonBySeasonId(VALID_SEASON_ID);

        assertEntityResponseModel(entity, seasonResponseModel);

    }

    @Test
    void whenValidSeasonId_thenDeleteSeason() {
        createEntity();

        seasonFinderService.deleteSeason(VALID_SEASON_ID);

        when(seasonRepository.existsSeasonBySeasonId(VALID_SEASON_ID)).thenReturn(false);
    }

    private void assertEntityStatsResponseModel(Season entity, SeasonStatsResponseModel seasonResponseModel){
        assertNotNull(seasonResponseModel);
        assertEquals(entity.getSeasonId(), seasonResponseModel.getSeasonId());
        assertEquals(entity.getYear(), seasonResponseModel.getYear());
        assertEquals(entity.getGp(), seasonResponseModel.getGp());
        assertEquals(entity.getPlayer().getPlayerId(), seasonResponseModel.getPlayerId());
        verify(seasonStatsResponseMapper, times(1)).entityToResponseModel(any(Season.class));
    }


    private void assertEntityResponseModel(Season entity, SeasonResponseModel seasonResponseModel){
        assertNotNull(seasonResponseModel);
        assertEquals(entity.getSeasonId(), seasonResponseModel.getSeasonId());
        assertEquals(entity.getYear(), seasonResponseModel.getYear());
        assertEquals(entity.getGp(), seasonResponseModel.getGp());
        assertEquals(entity.getPlayer().getPlayerId(), seasonResponseModel.getPlayerId());
        verify(seasonResponseMapper, times(1)).entityToResponseModel(any(Season.class));

    }

    private Season createEntity(){
        Player player = new Player();

        player.setPlayerId(VALID_PLAYER_ID);
        player.setTeamId(VALID_TEAM_ID);
        player.setAge(VALID_AGE);
        player.setPosition(VALID_POSITION);
        player.setLastName(VALID_LAST_NAME);
        player.setFirstName(VALID_FIRST_NAME);

        playerRepository.save(player);

        Season entity = new Season();

        entity.setPlayer(player);
        entity.setSeasonId(VALID_SEASON_ID);
        entity.setGp(VALID_GP);
        entity.setYear(VALID_YEAR);

        when(seasonRepository.save(any(Season.class))).thenAnswer(i -> i.getArguments()[0]);
        when(seasonRepository.existsSeasonBySeasonId(VALID_SEASON_ID)).thenReturn(true);
        when(seasonRepository.findSeasonBySeasonId(VALID_SEASON_ID)).thenReturn(entity);

        return entity;
    }
}