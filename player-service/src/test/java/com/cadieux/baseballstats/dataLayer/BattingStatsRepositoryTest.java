package com.cadieux.baseballstats.dataLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class BattingStatsRepositoryTest {

    private final Integer VALID_BATTING_ID = 1;

    BattingStats savedEntity;

    @Autowired
    BattingStatsRepository battingStatsRepository;

    @BeforeEach
    void setup_db(){
        battingStatsRepository.deleteAll();

        BattingStats entity = new BattingStats();
        entity.setBattingId(VALID_BATTING_ID);

        savedEntity = battingStatsRepository.save(entity);

        assertEquals(savedEntity.getBattingId(), entity.getBattingId());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenValidBattingStatsId_returnBattingStats() {
        BattingStats entity = battingStatsRepository.findBattingStatsByBattingId(VALID_BATTING_ID);

        assertEquals(savedEntity.getBattingId(), entity.getBattingId());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenSavingWithDuplicateBattingStatsId_thenThrowDataIntegrityException(){
        BattingStats entity = new BattingStats();
        entity.setBattingId(VALID_BATTING_ID);

        assertThrows(DataIntegrityViolationException.class, () -> {
            battingStatsRepository.save(entity);
        });
    }

    @Test
    void whenExistsBattingId_thenExistsBattingIdReturnsTrue() {
        assertEquals(battingStatsRepository.existsBattingStatsByBattingId(VALID_BATTING_ID), true);
    }
}