package com.baseballstats.team.businesslayer;

import com.baseballstats.team.datalayer.Team;
import com.baseballstats.team.datalayer.TeamRepository;
import com.baseballstats.team.exceptions.NotFoundException;
import com.baseballstats.team.mappinglayer.TeamRequestMapper;
import com.baseballstats.team.mappinglayer.TeamRequestModel;
import com.baseballstats.team.mappinglayer.TeamResponseMapper;
import com.baseballstats.team.mappinglayer.TeamResponseModel;
import com.baseballstats.team.util.ShortIdGen;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService{
    private TeamRepository teamRepository;
    private TeamResponseMapper teamResponseMapper;
    private TeamRequestMapper teamRequestMapper;

    public TeamServiceImpl(TeamRequestMapper teamRequestMapper, TeamRepository teamRepository, TeamResponseMapper teamResponseMapper){
        this.teamRepository = teamRepository;
        this.teamResponseMapper = teamResponseMapper;
        this.teamRequestMapper = teamRequestMapper;
    }

    @Override
    public TeamResponseModel findTeam(Integer teamId) {
        if(!teamRepository.existsTeamByTeamId(teamId)) throw new NotFoundException("No team with team id: " + teamId);
        Team team = teamRepository.findTeamByTeamId(teamId);

        return teamResponseMapper.entityToResponseModel(team);
    }

    @Override
    public TeamResponseModel createTeam(TeamRequestModel teamRequestModel) {
        Team team = teamRequestMapper.requestModelToEntity(teamRequestModel);

        Integer shortId = ShortIdGen.getShortId();

        while(teamRepository.existsTeamByTeamId(shortId)){
            shortId = ShortIdGen.getShortId();
        }

        team.setTeamId(shortId);

        Team saved = teamRepository.save(team);

        return teamResponseMapper.entityToResponseModel(saved);
    }

    @Override
    public TeamResponseModel updateTeam(TeamRequestModel teamRequestModel, Integer teamId) {
        if(!teamRepository.existsTeamByTeamId(teamId)) throw new NotFoundException("No team with team id: " + teamId);

        Team team = teamRequestMapper.requestModelToEntity(teamRequestModel);

        Team inDb = teamRepository.findTeamByTeamId(teamId);

        inDb.setCity(team.getCity());
        inDb.setName(team.getName());
        inDb.setLeagueId(team.getLeagueId());

        Team saved = teamRepository.save(inDb);

        return teamResponseMapper.entityToResponseModel(saved);
    }

    @Override
    @Transactional
    public void deleteTeam(Integer teamId) {
        if(!teamRepository.existsTeamByTeamId(teamId)) throw new NotFoundException("No team with team id: " + teamId);
        teamRepository.deleteTeamByTeamId(teamId);
    }

    @Override
    public List<TeamResponseModel> findTeamsByLeagueId(Integer leagueId) {
        List<Team> teams = teamRepository.findTeamsByLeagueId(leagueId);

        return teamResponseMapper.entityListToResponseModelList(teams);
    }

    @Override
    @Transactional
    public void deleteTeams(Integer leagueId) {
        teamRepository.deleteTeamsByLeagueId(leagueId);
    }
}
