package com.cadieux.baseballstats.presentationLayer;

import com.cadieux.baseballstats.dataLayer.BattingStatsRepository;
import com.cadieux.baseballstats.dataLayer.PitchingStatsRepository;
import com.cadieux.baseballstats.dataLayer.PlayerRepository;
import com.cadieux.baseballstats.dataLayer.SeasonRepository;
import com.cadieux.baseballstats.mapping.Player.PlayerRequestModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class PlayerListerControllerTest {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    WebTestClient webTestClient;

    private final String BASE_URI = "/api";
    private final Integer VALID_PLAYER_ID = 1;
    private final Integer NOT_FOUND_PLAYER_ID = 10000;
    private final Integer VALID_TEAM_ID = 1;

    private final String VALID_FIRST_NAME = "First name";
    private final String VALID_LAST_NAME = "Last name";
    private final String VALID_POSITION = "C";
    private final Integer VALID_AGE = 1;

    @AfterEach
    void tear_down(){
        playerRepository.deleteAll();
    }

    @Test
    void ifPlayersExist_thenPlayersShouldBeFound() {
        webTestClient.get()
                .uri(BASE_URI + "/players")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    @Test
    void ifPlayerExists_thenPlayerShouldBeFound() {
        webTestClient.get()
                .uri(BASE_URI + "/players/" + VALID_PLAYER_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.playerId").isEqualTo(VALID_PLAYER_ID);
    }

    @Test
    void ifPlayerDoesNotExists_thenPlayerShouldBeNotFound(){
        webTestClient.get()
                .uri(BASE_URI + "/players/" + NOT_FOUND_PLAYER_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/players/" + NOT_FOUND_PLAYER_ID)
                .jsonPath("$.message").isEqualTo("Unknown playerId provided: " + NOT_FOUND_PLAYER_ID);
    }

    @Test
    void whenCreatingPlayer_thenNewPlayerShouldBeFound() {
        PlayerRequestModel playerRequestModel = new PlayerRequestModel();

        playerRequestModel.setTeamId(VALID_TEAM_ID);
        playerRequestModel.setFirstName(VALID_FIRST_NAME);
        playerRequestModel.setLastName(VALID_LAST_NAME);
        playerRequestModel.setPosition(VALID_POSITION);
        playerRequestModel.setAge(VALID_AGE);

        webTestClient.post()
                .uri(BASE_URI + "/players")
                .accept(APPLICATION_JSON)
                .body(Mono.just(playerRequestModel), PlayerRequestModel.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.teamId").isEqualTo(VALID_TEAM_ID)
                .jsonPath("$.firstName").isEqualTo(VALID_FIRST_NAME)
                .jsonPath("$.lastName").isEqualTo(VALID_LAST_NAME)
                .jsonPath("$.position").isEqualTo(VALID_POSITION)
                .jsonPath("$.age").isEqualTo(VALID_AGE);
    }

    @Test
    void whenUpdatingPlayer_newInfoShouldBeFound() {
        PlayerRequestModel playerRequestModel = new PlayerRequestModel();

        playerRequestModel.setTeamId(VALID_TEAM_ID);
        playerRequestModel.setFirstName(VALID_FIRST_NAME);
        playerRequestModel.setLastName(VALID_LAST_NAME);
        playerRequestModel.setPosition(VALID_POSITION);
        playerRequestModel.setAge(VALID_AGE);

        webTestClient.put()
                .uri(BASE_URI + "/players/" + VALID_PLAYER_ID)
                .accept(APPLICATION_JSON)
                .body(Mono.just(playerRequestModel), PlayerRequestModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.teamId").isEqualTo(VALID_TEAM_ID)
                .jsonPath("$.firstName").isEqualTo(VALID_FIRST_NAME)
                .jsonPath("$.lastName").isEqualTo(VALID_LAST_NAME)
                .jsonPath("$.position").isEqualTo(VALID_POSITION)
                .jsonPath("$.age").isEqualTo(VALID_AGE)
                .jsonPath("$.playerId").isEqualTo(VALID_PLAYER_ID);
    }

    @Test
    void whenDeleteByPlayerId_shouldBeNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/players/" + VALID_PLAYER_ID)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void whenDeleteByInvalidPlayerId_shouldBeNotFoundException(){
        webTestClient.delete()
                .uri(BASE_URI + "/players/" + NOT_FOUND_PLAYER_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/players/" + NOT_FOUND_PLAYER_ID)
                .jsonPath("$.message").isEqualTo("Unknown playerId provided: " + NOT_FOUND_PLAYER_ID);
    }

    @Test
    void whenGetPlayerCareer_getCorrectPlayer() {
        webTestClient.get()
                .uri(BASE_URI + "/players/" + VALID_PLAYER_ID + "/stats")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.playerId").isEqualTo(VALID_PLAYER_ID);
    }

    @Test
    void whenGetPlayerSeasons_getCorrectPlayer() {
        webTestClient.get()
                .uri(BASE_URI + "/players/" + VALID_PLAYER_ID + "/seasons")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].playerId").isEqualTo(VALID_PLAYER_ID);
    }

    @Test
    void whenGetPlayersByTeam_getValidTeamId() {
        webTestClient.get()
                .uri(BASE_URI + "/teams/" + VALID_TEAM_ID + "/players")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].teamId").isEqualTo(VALID_TEAM_ID);
    }

    @Test
    void whenDeleteByTeam_getNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/teams/" + VALID_TEAM_ID + "/players")
                .exchange()
                .expectStatus().isNoContent();
    }
}