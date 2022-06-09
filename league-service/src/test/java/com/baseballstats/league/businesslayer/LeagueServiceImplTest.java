package com.baseballstats.league.businesslayer;

import com.baseballstats.league.datalayer.League;
import com.baseballstats.league.datalayer.LeagueRepository;
import com.baseballstats.league.exceptions.NotFoundException;
import com.baseballstats.league.mappinglayer.LeagueResponseMapper;
import com.baseballstats.league.mappinglayer.LeagueResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class LeagueServiceImplTest {

    @Autowired
    LeagueService leagueService;

    @MockBean
    LeagueRepository leagueRepository;

    @SpyBean
    LeagueResponseMapper leagueResponseMapper;

    private final Integer VALID_LEAGUE_ID = 10;
    private final Integer INVALID_LEAGUE_ID = 100;

    private final String VALID_NAME = "Name";
    private final String VALID_CATEGORY = "Category";

    @Test
    void whenValidLeagueId_returnLeagueResponseModel() {
        League entity = createEntity();

        LeagueResponseModel leagueResponseModel = leagueService.getLeagueByLeagueId(VALID_LEAGUE_ID);

        assertEntityResponseModel(entity, leagueResponseModel);
    }

    @Test
    void whenInvalidLeagueId_throwNotFound(){
        assertThrows(NotFoundException.class, () -> {
            leagueService.getLeagueByLeagueId(INVALID_LEAGUE_ID);
        });

        assertThrows(NotFoundException.class, () -> {
            leagueService.deleteLeague(INVALID_LEAGUE_ID);
        });
    }

    @Test
    void whenCreateLeague_returnLeagueResponseModel() {
        League entity = createEntity();

        LeagueResponseModel leagueResponseModel = leagueService.getLeagueByLeagueId(VALID_LEAGUE_ID);

        assertEntityResponseModel(entity, leagueResponseModel);
    }

    @Test
    void whenUpdateLeague_returnLeagueResponseModel() {
        League entity = createEntity();

        entity.setCategory("New Category");

        when(leagueRepository.save(any(League.class))).thenAnswer(i -> i.getArguments()[0]);

        LeagueResponseModel leagueResponseModel = leagueService.getLeagueByLeagueId(VALID_LEAGUE_ID);

        assertEntityResponseModel(entity, leagueResponseModel);
    }

    @Test
    void deleteLeague() {
        createEntity();

        leagueService.deleteLeague(VALID_LEAGUE_ID);

        when(leagueRepository.existsLeagueByLeagueId(VALID_LEAGUE_ID)).thenReturn(false);
    }

    private void assertEntityResponseModel(League entity, LeagueResponseModel leagueResponseModel){
        assertEquals(entity.getName(), leagueResponseModel.getName());
        assertEquals(entity.getLeagueId(), leagueResponseModel.getLeagueId());
        assertEquals(entity.getCategory(), leagueResponseModel.getCategory());
        verify(leagueResponseMapper, times(1)).entityToResponseModel(any(League.class));
    }

    private League createEntity(){
        League entity = new League();

        entity.setLeagueId(VALID_LEAGUE_ID);
        entity.setCategory(VALID_CATEGORY);
        entity.setName(VALID_NAME);

        when(leagueRepository.save(any(League.class))).thenAnswer(i -> i.getArguments()[0]);
        when(leagueRepository.existsLeagueByLeagueId(VALID_LEAGUE_ID)).thenReturn(true);
        when(leagueRepository.findLeagueByLeagueId(VALID_LEAGUE_ID)).thenReturn(entity);

        return entity;
    }
}