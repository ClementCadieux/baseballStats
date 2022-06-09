package com.cadieux.baseballstats.presentationLayer;

import com.cadieux.baseballstats.dataLayer.SeasonRepository;
import com.cadieux.baseballstats.mapping.Player.PlayerRequestModel;
import com.cadieux.baseballstats.mapping.Season.SeasonRequestModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = RANDOM_PORT,
        properties = {"spring.datasource.url=jdbc:h2:mem:baseballStats-db"})
@Sql({"/schema-h2.sql", "/data-h2.sql"})
@AutoConfigureWebTestClient
class SeasonListerControllerTest {

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    WebTestClient webTestClient;

    private final String BASE_URI = "/api";
    private final Integer VALID_SEASON_ID = 1;
    private final Integer INVALID_SEASON_ID = 101;
    private final Integer VALID_YEAR = 100;
    private final Integer VALID_GP = 1;

    private final Integer VALID_PLAYER_ID = 1;

    @AfterEach
    void tear_down(){
        seasonRepository.deleteAll();
    }

    @Test
    void whenSeasonsExist_getAllSeasons() {
        webTestClient.get()
                .uri(BASE_URI + "/seasons")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    @Test
    void whenSeasonExists_getSeasonByIdReturnsSeason() {
        webTestClient.get()
                .uri(BASE_URI + "/seasons/" + VALID_SEASON_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.seasonId").isEqualTo(VALID_SEASON_ID);
    }

    @Test
    void whenSeasonDoesntExist_getSeasonReturnsNotFound(){
        webTestClient.get()
                .uri(BASE_URI + "/seasons/" + INVALID_SEASON_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/seasons/" + INVALID_SEASON_ID)
                .jsonPath("$.message").isEqualTo("Unknown seasonId provided: " + INVALID_SEASON_ID);
    }

    @Test
    void whenCreatingSeason_newSeasonShouldBeFound() {
        SeasonRequestModel seasonRequestModel = new SeasonRequestModel();
        seasonRequestModel.setPlayerId(VALID_PLAYER_ID);
        seasonRequestModel.setYear(VALID_YEAR);
        seasonRequestModel.setGp(VALID_GP);
        seasonRequestModel.setBattingStatsId(-1);
        seasonRequestModel.setPitchingStatsId(-1);

        webTestClient.post()
                .uri(BASE_URI + "/seasons")
                .accept(APPLICATION_JSON)
                .body(Mono.just(seasonRequestModel), SeasonRequestModel.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.playerId").isEqualTo(VALID_PLAYER_ID)
                .jsonPath("$.year").isEqualTo(VALID_YEAR)
                .jsonPath("$.gp").isEqualTo(VALID_GP);
    }

    @Test
    void whenUpdatingSeason_newSeasonInfoShouldBeFound() {
        SeasonRequestModel seasonRequestModel = new SeasonRequestModel();
        seasonRequestModel.setPlayerId(VALID_PLAYER_ID);
        seasonRequestModel.setYear(VALID_YEAR);
        seasonRequestModel.setGp(VALID_GP);
        seasonRequestModel.setBattingStatsId(-1);
        seasonRequestModel.setPitchingStatsId(-1);

        webTestClient.put()
                .uri(BASE_URI + "/seasons/" + VALID_SEASON_ID)
                .accept(APPLICATION_JSON)
                .body(Mono.just(seasonRequestModel), SeasonRequestModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.playerId").isEqualTo(VALID_PLAYER_ID)
                .jsonPath("$.year").isEqualTo(VALID_YEAR)
                .jsonPath("$.gp").isEqualTo(VALID_GP)
                .jsonPath("$.seasonId").isEqualTo(VALID_SEASON_ID);
    }

    @Test
    void whenDeleteSeason_expectNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/seasons/" + VALID_SEASON_ID)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void whenDeleteWithInvalidId_expectNotFound(){
        webTestClient.delete()
                .uri(BASE_URI + "/seasons/" + INVALID_SEASON_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/seasons/" + INVALID_SEASON_ID)
                .jsonPath("$.message").isEqualTo("Unknown seasonId provided: " + INVALID_SEASON_ID);
    }

    @Test
    void whenGetSeasonStats_returnALlSeasonStats() {
        webTestClient.get()
                .uri(BASE_URI + "/seasons/" + VALID_SEASON_ID + "/stats")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.seasonId").isEqualTo(VALID_SEASON_ID);
    }
}