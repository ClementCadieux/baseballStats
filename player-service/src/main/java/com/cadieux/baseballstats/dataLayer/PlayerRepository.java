package com.cadieux.baseballstats.dataLayer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {
    public Player findPlayerByPlayerId(Integer playerId);

    boolean existsPlayerByPlayerId(Integer playerId);

    void deletePlayerByPlayerId(Integer playerId);

    List<Player> findPlayersByTeamId(Integer teamId);

    void deletePlayersByTeamId(Integer teamId);
}
