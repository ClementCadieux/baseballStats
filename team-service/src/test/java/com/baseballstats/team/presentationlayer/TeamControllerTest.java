package com.baseballstats.team.presentationlayer;

import com.baseballstats.team.datalayer.TeamRepository;
import com.baseballstats.team.mappinglayer.TeamRequestModel;
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
        properties = {"spring.datasource.url=jdbc:h2:mem:team-db"})
@Sql({"/schema-h2.sql", "/data-h2.sql"})
@AutoConfigureWebTestClient
class TeamControllerTest {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private WebTestClient webTestClient;

    private final String BASE_URI = "/api";

    private final Integer VALID_TEAM_ID = 1;
    private final Integer VALID_LEAGUE_ID = 1;
    private final String VALID_NAME = "name";
    private final String VALID_CITY = "city";

    private final Integer INVALID_TEAM_ID = 111;

    @AfterEach
    void tearDown(){
        teamRepository.deleteAll();
    }

    @Test
    void whenValidTeamId_getTeam() {
        webTestClient.get()
                .uri(BASE_URI + "/teams/" + VALID_TEAM_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.teamId").isEqualTo(VALID_TEAM_ID);
    }

    @Test
    void whenInvalidTeamId_getNotFound(){
        webTestClient.get()
                .uri(BASE_URI + "/teams/" + INVALID_TEAM_ID)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/teams/" + INVALID_TEAM_ID)
                .jsonPath("$.message").isEqualTo("No team with team id: " + INVALID_TEAM_ID);
    }

    @Test
    void whenCreatingTeam_thenTeamShouldBeFound() {
        TeamRequestModel teamRequestModel = new TeamRequestModel();

        teamRequestModel.setName(VALID_NAME);
        teamRequestModel.setCity(VALID_CITY);
        teamRequestModel.setLeagueId(VALID_LEAGUE_ID);

        webTestClient.post()
                .uri(BASE_URI + "/teams")
                .accept(APPLICATION_JSON)
                .body(Mono.just(teamRequestModel), TeamRequestModel.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo(VALID_NAME)
                .jsonPath("$.city").isEqualTo(VALID_CITY)
                .jsonPath("$.leagueId").isEqualTo(VALID_LEAGUE_ID);
    }

    @Test
    void whenUpdatingTeam_thenTeamShouldBeFound() {
        TeamRequestModel teamRequestModel = new TeamRequestModel();

        teamRequestModel.setName(VALID_NAME);
        teamRequestModel.setCity(VALID_CITY);
        teamRequestModel.setLeagueId(VALID_LEAGUE_ID);

        webTestClient.put()
                .uri(BASE_URI + "/teams/" + VALID_TEAM_ID)
                .accept(APPLICATION_JSON)
                .body(Mono.just(teamRequestModel), TeamRequestModel.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo(VALID_NAME)
                .jsonPath("$.city").isEqualTo(VALID_CITY)
                .jsonPath("$.leagueId").isEqualTo(VALID_LEAGUE_ID)
                .jsonPath("$.teamId").isEqualTo(VALID_TEAM_ID);
    }

    @Test
    void whenDeleteWithValidTeamId_expectNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/teams/" + VALID_TEAM_ID)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void whenDeleteWithInvalidTeamId_expectNotFound(){
        webTestClient.delete()
                .uri(BASE_URI + "/teams/" + INVALID_TEAM_ID)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.path").isEqualTo("uri=" + BASE_URI + "/teams/" + INVALID_TEAM_ID)
                .jsonPath("$.message").isEqualTo("No team with team id: " + INVALID_TEAM_ID);
    }

    @Test
    void whenValidLeagueId_getListOfTeams() {
        webTestClient.get()
                .uri(BASE_URI + "/leagues/" + VALID_LEAGUE_ID + "/teams")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$[0].leagueId").isEqualTo(VALID_LEAGUE_ID);
    }

    @Test
    void whenDeleteWithValidLeagueId_expectNoContent() {
        webTestClient.delete()
                .uri(BASE_URI + "/leagues/" + VALID_LEAGUE_ID + "/teams")
                .exchange()
                .expectStatus().isNoContent();
    }
}