package com.cadieux.baseballstats.businessLayer;

import com.cadieux.baseballstats.dataLayer.Player;
import com.cadieux.baseballstats.dataLayer.PlayerRepository;
import com.cadieux.baseballstats.dataLayer.Season;
import com.cadieux.baseballstats.dataLayer.SeasonRepository;
import com.cadieux.baseballstats.exceptions.NotFoundException;
import com.cadieux.baseballstats.mapping.Player.PlayerRequestMapper;
import com.cadieux.baseballstats.mapping.Player.PlayerRequestModel;
import com.cadieux.baseballstats.mapping.Player.PlayerResponseMapper;
import com.cadieux.baseballstats.mapping.Player.PlayerResponseModel;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseMapper;
import com.cadieux.baseballstats.mapping.Season.SeasonResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.PlayerCareerResponseMapper;
import com.cadieux.baseballstats.mapping.StatsDisplayers.PlayerCareerResponseModel;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseMapper;
import com.cadieux.baseballstats.mapping.StatsDisplayers.SeasonStatsResponseModel;
import com.cadieux.baseballstats.util.ShortIdGen;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerFinderServiceImpl implements PlayerFinderService{
    private PlayerRepository playerRepository;
    private PlayerRequestMapper playerRequestMapper;
    private PlayerResponseMapper playerResponseMapper;
    private PlayerCareerResponseMapper playerCareerResponseMapper;
    private SeasonStatsResponseMapper seasonStatsResponseMapper;
    private SeasonRepository seasonRepository;
    private SeasonResponseMapper seasonResponseMapper;

    public PlayerFinderServiceImpl(SeasonResponseMapper seasonResponseMapper, SeasonRepository seasonRepository, SeasonStatsResponseMapper seasonStatsResponseMapper, PlayerCareerResponseMapper playerCareerResponseMapper, PlayerRepository playerRepository, PlayerRequestMapper playerRequestMapper, PlayerResponseMapper playerResponseMapper){
        this.playerRepository = playerRepository;
        this.playerRequestMapper = playerRequestMapper;
        this.playerResponseMapper = playerResponseMapper;
        this.playerCareerResponseMapper = playerCareerResponseMapper;
        this.seasonStatsResponseMapper = seasonStatsResponseMapper;
        this.seasonRepository = seasonRepository;
        this.seasonResponseMapper = seasonResponseMapper;
    }

    @Override
    public List<PlayerResponseModel> findAllPlayers() {
        List<Player> players = (List<Player>)playerRepository.findAll();

        return playerResponseMapper.entityListToResponseModelList(players);
    }

    @Override
    public PlayerResponseModel findPlayerByPlayerId(Integer playerId) {
        if(!playerRepository.existsPlayerByPlayerId(playerId)) throw new NotFoundException("Unknown playerId provided: " + playerId);
        Player player = playerRepository.findPlayerByPlayerId(playerId);

        return playerResponseMapper.entityToResponseModel(player);
    }

    @Override
    public PlayerResponseModel createPlayer(PlayerRequestModel requestModel) {
        Player player = playerRequestMapper.requestModelToEntity(requestModel);

        Integer shortId = ShortIdGen.getShortId();

        while(playerRepository.existsPlayerByPlayerId(shortId)){
            shortId = ShortIdGen.getShortId();
        }

        player.setPlayerId(shortId);

        player = playerRepository.save(player);

        return playerResponseMapper.entityToResponseModel(player);
    }

    @Override
    public PlayerResponseModel updatePlayer(PlayerRequestModel requestModel, Integer playerId) {
        if(playerRepository.existsPlayerByPlayerId(playerId)){
            Player inDbPlayer = playerRepository.findPlayerByPlayerId(playerId);

            Player newPlayer = playerRequestMapper.requestModelToEntity(requestModel);

            inDbPlayer.setFirstName(newPlayer.getFirstName());
            inDbPlayer.setLastName(newPlayer.getLastName());
            inDbPlayer.setPosition(newPlayer.getPosition());
            inDbPlayer.setAge(newPlayer.getAge());

            inDbPlayer = playerRepository.save(inDbPlayer);

            return playerResponseMapper.entityToResponseModel(inDbPlayer);
        }

        throw new NotFoundException("Unknown playerId provided: " + playerId);
    }

    @Override
    @Transactional
    public void deletePlayer(Integer playerId) {
        if(playerRepository.existsPlayerByPlayerId(playerId)) {
            playerRepository.deletePlayerByPlayerId(playerId);
            return;
        }
        throw new NotFoundException("Unknown playerId provided: " + playerId);
    }

    @Override
    public PlayerCareerResponseModel getPlayerCareer(Integer playerId) {
        if(!playerRepository.existsPlayerByPlayerId(playerId)) throw new NotFoundException("Unknown playerId provided:" + playerId);

        Player player = playerRepository.findPlayerByPlayerId(playerId);

        PlayerCareerResponseModel playerCareerResponseModel = playerCareerResponseMapper.entityToResponseModel(player);

        List<Season> seasons = new ArrayList<>(player.getSeasons());

        playerCareerResponseModel.setSeasons(seasonStatsResponseMapper.entityListToResponseModelList(seasons));

        setCareerTotals(playerCareerResponseModel);

        return playerCareerResponseModel;
    }

    @Override
    public List<SeasonResponseModel> getSeasonsByPlayerId(Integer playerId) {
        if(playerRepository.existsPlayerByPlayerId(playerId)){
            List<Season> seasons = seasonRepository.findSeasonsByPlayer(playerRepository.findPlayerByPlayerId(playerId));

            return seasonResponseMapper.entityListToResponseModelList(seasons);
        }
        throw new NotFoundException("Unknown playerId provided: " + playerId);
    }

    @Override
    public List<PlayerResponseModel> getPlayersByTeamId(Integer teamId) {
        List<Player> players = playerRepository.findPlayersByTeamId(teamId);
        List<PlayerResponseModel> playerResponseModels = playerResponseMapper.entityListToResponseModelList(players);
        return playerResponseModels;
    }

    @Override
    @Transactional
    public void deletePlayersByTeam(Integer teamId) {
        playerRepository.deletePlayersByTeamId(teamId);
    }

    private void setCareerTotals(PlayerCareerResponseModel playerCareerResponseModel) {
        Integer gp = 0;
        //In db stats
        Integer ab = 0;
        Integer pa = 0;
        Integer bb = 0;
        Integer singles = 0;
        Integer doubles = 0;
        Integer triples = 0;
        Integer hr = 0;
        Integer hbp = 0;

        //Derived stats
        Integer b_h = 0;
        double avg = 0;
        double obp = 0;

        //In db stats
        Integer ip = 0;
        Integer gs = 0;
        Integer sv = 0;
        Integer h = 0;
        Integer er = 0;
        Integer k = 0;
        Integer p_bb = 0;
        Integer w = 0;
        Integer l = 0;

        //Derived stats
        double era = 0;
        double wPercentage = 0;

        for(SeasonStatsResponseModel season : playerCareerResponseModel.getSeasons()){
            gp += season.getGp();

            ab += season.getAb();
            pa += season.getPa();
            bb += season.getBb();
            singles += season.getSingles();
            doubles += season.getDoubles();
            triples += season.getTriples();
            hr += season.getHr();
            hbp += season.getHbp();

            ip += season.getIp();
            gs += season.getGs();
            sv += season.getSv();
            h += season.getH();
            er += season.getEr();
            k += season.getK();
            p_bb += season.getP_bb();
            w += season.getW();
            l += season.getL();
        }

        b_h = singles + doubles + triples + hr;
        avg = ab == 0 ? 0 : (double)b_h/ab;
        obp = pa == 0 ? 0 : (double)(b_h + bb) / pa;

        era = ip == 0 ? 0 : (double)er/ip * 9;
        wPercentage = (w + l) == 0 ? 0 : (double) w / (w + l);

        playerCareerResponseModel.setGp(gp);

        playerCareerResponseModel.setAb(ab);
        playerCareerResponseModel.setPa(pa);
        playerCareerResponseModel.setBb(bb);
        playerCareerResponseModel.setSingles(singles);
        playerCareerResponseModel.setDoubles(doubles);
        playerCareerResponseModel.setTriples(triples);
        playerCareerResponseModel.setHr(hr);
        playerCareerResponseModel.setHbp(hbp);

        playerCareerResponseModel.setB_h(b_h);
        playerCareerResponseModel.setAvg(avg);
        playerCareerResponseModel.setObp(obp);

        playerCareerResponseModel.setIp(ip);
        playerCareerResponseModel.setGs(gs);
        playerCareerResponseModel.setSv(sv);
        playerCareerResponseModel.setH(h);
        playerCareerResponseModel.setEr(er);
        playerCareerResponseModel.setK(k);
        playerCareerResponseModel.setP_bb(p_bb);
        playerCareerResponseModel.setW(w);
        playerCareerResponseModel.setL(l);

        playerCareerResponseModel.setEra(era);
        playerCareerResponseModel.setWPercentage(wPercentage);
    }
}
