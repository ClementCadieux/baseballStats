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
class PitchingStatsRepositoryTest {

    private final Integer VALID_PITCHING_ID = 1;

    PitchingStats savedEntity;

    @Autowired
    PitchingStatsRepository pitchingStatsRepository;

    @BeforeEach
    void setup_db(){
        pitchingStatsRepository.deleteAll();

        PitchingStats entity = new PitchingStats();
        entity.setPitchingId(VALID_PITCHING_ID);

        savedEntity = pitchingStatsRepository.save(entity);

        assertEquals(savedEntity.getPitchingId(), entity.getPitchingId());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenValidPitchingStatsId_returnPitchingStats() {
        PitchingStats entity = pitchingStatsRepository.findPitchingStatsByPitchingId(VALID_PITCHING_ID);

        assertEquals(savedEntity.getPitchingId(), entity.getPitchingId());
        assertNotNull(savedEntity.getId());
    }

    @Test
    void whenSavingWithDuplicatePitchingStatsId_thenThrowDataIntegrityException(){
        PitchingStats entity = new PitchingStats();
        entity.setPitchingId(VALID_PITCHING_ID);

        assertThrows(DataIntegrityViolationException.class, () -> {
            pitchingStatsRepository.save(entity);
        });
    }

    @Test
    void whenExistsPitchingId_thenExistsPitchingIdReturnsTrue() {
        assertEquals(pitchingStatsRepository.existsPitchingStatsByPitchingId(VALID_PITCHING_ID), true);
    }
}