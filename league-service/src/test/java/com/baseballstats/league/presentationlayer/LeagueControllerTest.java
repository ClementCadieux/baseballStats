package com.baseballstats.league.presentationlayer;

import com.baseballstats.league.datalayer.League;
import com.baseballstats.league.datalayer.LeagueRepository;
import com.baseballstats.league.mappinglayer.LeagueRequestModel;
import com.baseballstats.league.mappinglayer.LeagueResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureWebTestClient
class LeagueControllerTest {

    private final String BASE_URI = "/api/leagues";
    private final Integer VALID_LEAGUE_ID = 1;
    private final Integer INVALID_LEAGUE_ID = 100;

    private final String VALID_NAME = "Name";
    private final String VALID_CATEGORY = "Category";

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    WebTestClient webTestClient;
    @BeforeEach
    void setup_db(){
        leagueRepository.deleteAll();

        League entity = new League();

        entity.setLeagueId(VALID_LEAGUE_ID);
        entity.setCategory(VALID_CATEGORY);
        entity.setName(VALID_NAME);

        leagueRepository.save(entity);
    }

    @AfterEach
    void tear_down(){
        leagueRepository.deleteAll();
    }

    @Test
    void ifValidLeagueId_thenLeagueShouldBeFound() {
        webTestClient.get()
                .uri(BASE_URI + "/" + VALID_LEAGUE_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.leagueId").isEqualTo(VALID_LEAGUE_ID)
                .jsonPath("$.category").isEqualTo(VALID_CATEGORY)
                .jsonPath("$.name").isEqualTo(VALID_NAME);
    }

    @Test
    void ifInvalidLeagueId_thenLeagueShouldBeNotFound(){
        webTestClient.get()
                .uri(BASE_URI + "/" + INVALID_LEAGUE_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/" + INVALID_LEAGUE_ID)
                .jsonPath("$.message").isEqualTo("No league with league id: " + INVALID_LEAGUE_ID);
    }

    @Test
    void whenLeagueCreated_thenLeagueShouldBeFound() {
        LeagueRequestModel entity = new LeagueRequestModel();

        entity.setCategory(VALID_CATEGORY);
        entity.setName(VALID_NAME);

        webTestClient.post()
                .uri(BASE_URI)
                .accept(APPLICATION_JSON)
                .body(Mono.just(entity), LeagueRequestModel.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.category").isEqualTo(VALID_CATEGORY)
                .jsonPath("$.name").isEqualTo(VALID_NAME);
    }

    @Test
    void whenLeagueUpdated_thenLeagueShouldBeFound() {
        LeagueRequestModel entity = new LeagueRequestModel();

        entity.setCategory(VALID_CATEGORY);
        entity.setName(VALID_NAME);

        webTestClient.put()
                .uri(BASE_URI + "/" + VALID_LEAGUE_ID)
                .accept(APPLICATION_JSON)
                .body(Mono.just(entity), LeagueRequestModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.leagueId").isEqualTo(VALID_LEAGUE_ID)
                .jsonPath("$.category").isEqualTo(VALID_CATEGORY)
                .jsonPath("$.name").isEqualTo(VALID_NAME);
    }

    @Test
    void whenLeagueDelete_noContentShouldBeReturned() {
        webTestClient.delete()
                .uri(BASE_URI + "/" + VALID_LEAGUE_ID)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void whenDeleteByInvalidLeagueId_throwNotFound(){
        webTestClient.get()
                .uri(BASE_URI + "/" + INVALID_LEAGUE_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/" + INVALID_LEAGUE_ID)
                .jsonPath("$.message").isEqualTo("No league with league id: " + INVALID_LEAGUE_ID);
    }
}