package com.baseballstats.team.businesslayer;

import com.baseballstats.team.datalayer.Team;
import com.baseballstats.team.datalayer.TeamRepository;
import com.baseballstats.team.exceptions.NotFoundException;
import com.baseballstats.team.mappinglayer.TeamResponseMapper;
import com.baseballstats.team.mappinglayer.TeamResponseModel;
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
class TeamServiceImplTest {

    @Autowired
    TeamService teamService;

    @MockBean
    TeamRepository teamRepository;

    @SpyBean
    TeamResponseMapper teamResponseMapper;

    private final Integer VALID_TEAM_ID = 100;
    private final Integer VALID_LEAGUE_ID = 1;
    private final String VALID_NAME = "name";
    private final String VALID_CITY = "city";

    private final Integer INVALID_TEAM_ID = 111;

    @Test
    void whenValidTeamId_findTeamResponseModel() {
        Team entity = createEntity();

        TeamResponseModel found = teamService.findTeam(VALID_TEAM_ID);

        assertTeamResponseModel(entity, found);
    }

    @Test
    void whenInvalidTeamId_NotFoundExceptionShouldBeThrown(){
        assertThrows(NotFoundException.class, () -> {
            teamService.findTeam(INVALID_TEAM_ID);
        });

        assertThrows(NotFoundException.class, () -> {
            teamService.deleteTeam(INVALID_TEAM_ID);
        });
    }

    @Test
    void whenCreatingTeam_teamShouldBeFound() {
        Team entity = createEntity();

        TeamResponseModel found = teamService.findTeam(VALID_TEAM_ID);

        assertTeamResponseModel(entity, found);
    }

    @Test
    void whenUpdatingTeam_teamShouldBeFound() {
        Team entity = createEntity();

        entity.setName("New Name");

        when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArguments()[0]);

        TeamResponseModel found = teamService.findTeam(VALID_TEAM_ID);

        assertTeamResponseModel(entity, found);
    }

    @Test
    void whenValidTeamId_thenShouldBeDeleted() {
        createEntity();

        teamService.deleteTeam(VALID_TEAM_ID);

        when(teamRepository.existsTeamByTeamId(VALID_TEAM_ID)).thenReturn(false);
    }

    @Test
    void whenValidLeagueId_ListLeagueResponseModel() {
        Team entity = createEntity();

        List<TeamResponseModel> found = teamService.findTeamsByLeagueId(VALID_LEAGUE_ID);

        assertTeamResponseModelList(entity, found);
    }

    @Test
    void whenValidLeagueID_DeleteTeams() {
        createEntity();

        teamService.deleteTeams(VALID_LEAGUE_ID);

        when(teamRepository.existsTeamByTeamId(VALID_TEAM_ID)).thenReturn(false);
    }

    private void assertTeamResponseModelList(Team entity, List<TeamResponseModel> teamResponseModels){
        assertNotNull(teamResponseModels);

        for (TeamResponseModel teamResponseModel:
             teamResponseModels) {
            assertNotNull(teamResponseModel);
            assertEquals(entity.getLeagueId(), teamResponseModel.getLeagueId());
        }

        verify(teamResponseMapper, times(1)).entityListToResponseModelList(any(List.class));
    }

    private void assertTeamResponseModel(Team entity, TeamResponseModel teamResponseModel){
        assertNotNull(teamResponseModel);
        assertEquals(entity.getCity(), teamResponseModel.getCity());
        assertEquals(entity.getTeamId(), teamResponseModel.getTeamId());
        assertEquals(entity.getLeagueId(), teamResponseModel.getLeagueId());
        assertEquals(entity.getName(), teamResponseModel.getName());
        verify(teamResponseMapper, times(1)).entityToResponseModel(any(Team.class));
    }

    private Team createEntity(){
        Team entity = new Team();

        entity.setName(VALID_NAME);
        entity.setCity(VALID_CITY);
        entity.setTeamId(VALID_TEAM_ID);
        entity.setLeagueId(VALID_LEAGUE_ID);

        when(teamRepository.save(any(Team.class))).thenAnswer(i -> i.getArguments()[0]);
        when(teamRepository.existsTeamByTeamId(VALID_TEAM_ID)).thenReturn(true);
        when(teamRepository.findTeamByTeamId(VALID_TEAM_ID)).thenReturn(entity);

        return entity;
    }
}