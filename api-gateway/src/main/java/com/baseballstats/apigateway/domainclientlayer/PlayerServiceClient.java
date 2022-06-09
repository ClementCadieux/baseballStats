package com.baseballstats.apigateway.domainclientlayer;

import com.baseballstats.apigateway.exceptions.HttpErrorInfo;
import com.baseballstats.apigateway.exceptions.InvalidInputException;
import com.baseballstats.apigateway.exceptions.NotFoundException;
import com.baseballstats.apigateway.mappinglayer.PlayerRequestModel;
import com.baseballstats.apigateway.mappinglayer.PlayerResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class PlayerServiceClient {
    private String url;

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    public PlayerServiceClient(@Value("${app.players.host}") String teamsServiceHost, @Value("${app.players.port}") String teamsServicePort, ObjectMapper mapper, RestTemplate restTemplate){
        this.url = "http://" + teamsServiceHost + ":" + teamsServicePort + "/api";

        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public List<PlayerResponseModel> getPlayersDetails(Integer teamId){
        String requestUrl = url + "/teams/" + teamId + "/players";
        List<PlayerResponseModel> players;
        try {
            players = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PlayerResponseModel>>() {
                    }).getBody();
        }
        catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }

        return players;
    }

    public PlayerResponseModel createPlayer(PlayerRequestModel playerRequestModel){
        String requestUrl = url + "/players";
        PlayerResponseModel playerResponseModel;

        try {
            playerResponseModel = restTemplate.postForObject(requestUrl, playerRequestModel, PlayerResponseModel.class);
        } catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return playerResponseModel;
    }

    public void deletePlayers(Integer teamId){
        String requestUrl = url + "/teams/" + teamId + "/players";

        try {
            restTemplate.delete(requestUrl);
        } catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        switch (ex.getStatusCode()) {
            case NOT_FOUND:
                return new NotFoundException(getErrorMessage(ex));
            case UNPROCESSABLE_ENTITY :
                return new InvalidInputException(getErrorMessage(ex));
            default:
                log.warn("Got an unexpected HTTP error: {}, will rethrow it",
                        ex.getStatusCode());
                log.warn("Error body: {}", ex.getResponseBodyAsString());
                return ex;
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }
        catch (IOException ioex) {
            return ioex.getMessage();
        }
    }
}
