package com.cadieux.baseballstats.businessLayer;

import com.cadieux.baseballstats.dataLayer.*;
import com.cadieux.baseballstats.exceptions.NotFoundException;
import com.cadieux.baseballstats.mapping.Season.SeasonRequestMapper;
import com.cadieux.baseballstats.mapping.Season.SeasonRequestModel;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseMapper;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseMapper;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseModel;
import com.cadieux.baseballstats.util.ShortIdGen;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SeasonFinderServiceImpl implements SeasonFinderService{
    private SeasonRepository seasonRepository;
    private SeasonRequestMapper seasonRequestMapper;
    private SeasonResponseMapper seasonResponseMapper;
    private BattingStatsRepository battingStatsRepository;
    private PitchingStatsRepository pitchingStatsRepository;
    private PlayerRepository playerRepository;
    private SeasonStatsResponseMapper seasonStatsResponseMapper;

    public SeasonFinderServiceImpl(SeasonStatsResponseMapper seasonStatsResponseMapper, PlayerRepository playerRepository, SeasonRepository seasonRepository, SeasonRequestMapper seasonRequestMapper, SeasonResponseMapper seasonResponseMapper, BattingStatsRepository battingStatsRepository, PitchingStatsRepository pitchingStatsRepository){
        this.seasonRepository = seasonRepository;
        this.seasonRequestMapper = seasonRequestMapper;
        this.seasonResponseMapper = seasonResponseMapper;
        this.battingStatsRepository = battingStatsRepository;
        this.pitchingStatsRepository = pitchingStatsRepository;
        this.playerRepository = playerRepository;
        this.seasonStatsResponseMapper = seasonStatsResponseMapper;
    }
    @Override
    public List<SeasonResponseModel> getAllSeasons() {
        List<Season> seasons = (List<Season>)seasonRepository.findAll();

        return seasonResponseMapper.entityListToResponseModelList(seasons);
    }

    @Override
    public SeasonResponseModel getSeasonBySeasonId(Integer seasonId) {
        if(!seasonRepository.existsSeasonBySeasonId(seasonId)) throw new NotFoundException("Unknown seasonId provided: " + seasonId);
        Season season = seasonRepository.findSeasonBySeasonId(seasonId);

        return seasonResponseMapper.entityToResponseModel(season);
    }

    @Override
    public SeasonResponseModel createSeason(SeasonRequestModel requestModel) {
        Season season = seasonRequestMapper.requestModelToEntity(requestModel);

        Integer shortId = ShortIdGen.getShortId();

        while(seasonRepository.existsSeasonBySeasonId(shortId)){
            shortId = ShortIdGen.getShortId();
        }

        season.setSeasonId(shortId);

        if(!playerRepository.existsPlayerByPlayerId(requestModel.getPlayerId()))
            throw new NotFoundException("Unknown playerId provided: " + requestModel.getPlayerId());

        season.setPlayer(playerRepository.findPlayerByPlayerId(requestModel.getPlayerId()));

        statsHandler(season, requestModel);

        //setBattingAndPitchingStats(requestModel, season);

        season = seasonRepository.save(season);

        return seasonResponseMapper.entityToResponseModel(season);
    }


    @Override
    public SeasonResponseModel updateSeason(SeasonRequestModel requestModel, Integer seasonId) {
        if(seasonRepository.existsSeasonBySeasonId(seasonId)){
            Season inDbSeason = seasonRepository.findSeasonBySeasonId(seasonId);

            Season newSeason = seasonRequestMapper.requestModelToEntity(requestModel);

            //setBattingAndPitchingStats(requestModel, inDbSeason);

            if(!playerRepository.existsPlayerByPlayerId(requestModel.getPlayerId()))
                throw new NotFoundException("Unknown playerId provided: " + requestModel.getPlayerId());

            inDbSeason.setPlayer(playerRepository.findPlayerByPlayerId(requestModel.getPlayerId()));
            inDbSeason.setGp(newSeason.getGp());
            inDbSeason.setYear(newSeason.getYear());

            statsHandler(inDbSeason, requestModel);

            inDbSeason = seasonRepository.save(inDbSeason);

            return seasonResponseMapper.entityToResponseModel(inDbSeason);
        }

        throw new NotFoundException("Unknown seasonId provided: " + seasonId);
    }

    @Override
    @Transactional
    public void deleteSeason(Integer seasonId) {
        if(seasonRepository.existsSeasonBySeasonId(seasonId)){
            seasonRepository.deleteSeasonBySeasonId(seasonId);
            return;
        }
        throw new NotFoundException("Unknown seasonId provided: " + seasonId);
    }

    @Override
    public SeasonStatsResponseModel getSeasonStats(Integer seasonId) {
        if(seasonRepository.existsSeasonBySeasonId(seasonId)){
            Season season = seasonRepository.findSeasonBySeasonId(seasonId);

            return seasonStatsResponseMapper.entityToResponseModel(season);
        }
        throw new NotFoundException("Unknown seasonId provided: " + seasonId);
    }


    private void statsHandler(Season season, SeasonRequestModel requestModel) {
        BattingStats battingStats;

        if(requestModel.getBattingStatsId() == -1){
            battingStats = new BattingStats();
            Integer shortId = ShortIdGen.getShortId();

            while(battingStatsRepository.existsBattingStatsByBattingId(shortId)){
                shortId = ShortIdGen.getShortId();
            }

            battingStats.setBattingId(shortId);
        } else {
            if(battingStatsRepository.existsBattingStatsByBattingId(requestModel.getBattingStatsId())) battingStats = battingStatsRepository.findBattingStatsByBattingId(requestModel.getBattingStatsId());
            else throw new NotFoundException("Unknown battingStatsId provided: " + requestModel.getBattingStatsId());
        }

        battingStats.setHr(requestModel.getHr());
        battingStats.setSingles(requestModel.getSingles());
        battingStats.setHbp(requestModel.getHbp());
        battingStats.setPa(requestModel.getPa());
        battingStats.setAb(requestModel.getAb());
        battingStats.setBb(requestModel.getBb());
        battingStats.setDoubles(requestModel.getDoubles());
        battingStats.setTriples(requestModel.getTriples());

        battingStats = battingStatsRepository.save(battingStats);

        season.setBattingStats(battingStats);

        PitchingStats pitchingStats;

        if(requestModel.getPitchingStatsId() == -1){
            pitchingStats = new PitchingStats();

            Integer shortId = ShortIdGen.getShortId();

            while(pitchingStatsRepository.existsPitchingStatsByPitchingId(shortId))
                shortId = ShortIdGen.getShortId();

            pitchingStats.setPitchingId(shortId);
        } else {
            if(pitchingStatsRepository.existsPitchingStatsByPitchingId(requestModel.getPitchingStatsId())) pitchingStats = pitchingStatsRepository.findPitchingStatsByPitchingId(requestModel.getPitchingStatsId());
            else throw new NotFoundException("Unknown pitchingStatsId provided: " + requestModel.getPitchingStatsId());
        }

        pitchingStats.setBb(requestModel.getP_bb());
        pitchingStats.setEr(requestModel.getEr());
        pitchingStats.setGs(requestModel.getGs());
        pitchingStats.setH(requestModel.getH());
        pitchingStats.setIp(requestModel.getIp());
        pitchingStats.setK(requestModel.getK());
        pitchingStats.setL(requestModel.getL());
        pitchingStats.setSv(requestModel.getSv());
        pitchingStats.setW(requestModel.getW());

        pitchingStats = pitchingStatsRepository.save(pitchingStats);

        season.setPitchingStats(pitchingStats);
    }

    /*private void setBattingAndPitchingStats(SeasonRequestModel requestModel, Season inDbSeason){
        if(requestModel.getBattingStatsId() == -1) inDbSeason.setBattingStats(null);
        else inDbSeason.setBattingStats(battingStatsRepository.findBattingStatsByBattingId(requestModel.getBattingStatsId()));

        if(requestModel.getPitchingStatsId() == -1) inDbSeason.setPitchingStats(null);
        else inDbSeason.setPitchingStats(pitchingStatsRepository.findPitchingStatsByPitchingId(requestModel.getPitchingStatsId()));
    }*/
}
