package com.baseballstats.league.businesslayer;

import com.baseballstats.league.datalayer.League;
import com.baseballstats.league.datalayer.LeagueRepository;
import com.baseballstats.league.exceptions.NotFoundException;
import com.baseballstats.league.mappinglayer.LeagueRequestMapper;
import com.baseballstats.league.mappinglayer.LeagueRequestModel;
import com.baseballstats.league.mappinglayer.LeagueResponseMapper;
import com.baseballstats.league.mappinglayer.LeagueResponseModel;
import com.baseballstats.league.util.ShortIdGen;
import org.springframework.stereotype.Service;

@Service
public class LeagueServiceImpl implements LeagueService{

    private LeagueRepository leagueRepository;
    private LeagueResponseMapper leagueResponseMapper;
    private LeagueRequestMapper leagueRequestMapper;

    public LeagueServiceImpl(LeagueRepository leagueRepository, LeagueResponseMapper leagueResponseMapper, LeagueRequestMapper leagueRequestMapper) {
        this.leagueRepository = leagueRepository;
        this.leagueResponseMapper = leagueResponseMapper;
        this.leagueRequestMapper = leagueRequestMapper;
    }

    @Override
    public LeagueResponseModel getLeagueByLeagueId(Integer leagueId) {
        if(!leagueRepository.existsLeagueByLeagueId(leagueId)) throw new NotFoundException("No league with league id: " + leagueId);

        League league = leagueRepository.findLeagueByLeagueId(leagueId);

        return leagueResponseMapper.entityToResponseModel(league);
    }

    @Override
    public LeagueResponseModel createLeague(LeagueRequestModel leagueRequestModel) {
        League league = leagueRequestMapper.requestModelToEntity(leagueRequestModel);

        Integer shortId = ShortIdGen.getShortId();

        while(leagueRepository.existsLeagueByLeagueId(shortId)){
            shortId = ShortIdGen.getShortId();
        }

        league.setLeagueId(shortId);

        League saved = leagueRepository.save(league);

        return leagueResponseMapper.entityToResponseModel(saved);
    }

    @Override
    public LeagueResponseModel updateLeague(LeagueRequestModel leagueRequestModel, Integer leagueId) {
        if(!leagueRepository.existsLeagueByLeagueId(leagueId)) throw new NotFoundException("No league with league id: " + leagueId);
        League league = leagueRepository.findLeagueByLeagueId(leagueId);

        league.setCategory(leagueRequestModel.getCategory());
        league.setName(leagueRequestModel.getName());

        League saved = leagueRepository.save(league);

        return leagueResponseMapper.entityToResponseModel(saved);
    }

    @Override
    public void deleteLeague(Integer leagueId) {
        if(!leagueRepository.existsLeagueByLeagueId(leagueId)) throw new NotFoundException("No league with league id: " + leagueId);
        leagueRepository.deleteLeagueByLeagueId(leagueId);
    }
}
