package com.baseballstats.apigateway.domainclientlayer;

import com.baseballstats.apigateway.exceptions.HttpErrorInfo;
import com.baseballstats.apigateway.exceptions.InvalidInputException;
import com.baseballstats.apigateway.exceptions.NotFoundException;
import com.baseballstats.apigateway.mappinglayer.LeagueRequestModel;
import com.baseballstats.apigateway.mappinglayer.LeagueResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Slf4j
@Service
public class LeagueServiceClient {
    private String url;

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    public LeagueServiceClient(@Value("${app.leagues.host}") String leaguesServiceHost, @Value("${app.leagues.port}") String leaguesServicePort, ObjectMapper mapper, RestTemplate restTemplate){
        this.url = "http://" + leaguesServiceHost + ":" + leaguesServicePort + "/api";

        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public LeagueResponseModel getLeagueById(Integer leagueId){
        LeagueResponseModel leagueResponseModel;
        try {
            String requestUrl = url + "/leagues/" + leagueId;
            leagueResponseModel = restTemplate.getForObject(requestUrl, LeagueResponseModel.class);
        } catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return leagueResponseModel;
    }

    public LeagueResponseModel createLeague(LeagueRequestModel leagueRequestModel){
        LeagueResponseModel leagueResponseModel;
        try {
            String requestUrl = url + "/leagues";
            leagueResponseModel = restTemplate.postForObject(requestUrl, leagueRequestModel, LeagueResponseModel.class);
        } catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return leagueResponseModel;
    }

    public LeagueResponseModel updateLeague(LeagueRequestModel leagueRequestModel, Integer leagueId){
        LeagueResponseModel leagueResponseModel;
        try {
            String requestUrl = url + "/leagues/" + leagueId;
            restTemplate.put(requestUrl, leagueRequestModel);
            leagueResponseModel = restTemplate.getForObject(requestUrl, LeagueResponseModel.class);
        } catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return leagueResponseModel;
    }

    public void deleteLeague(Integer leagueId) {
        try {
            String requestUrl = url + "/leagues/" + leagueId;
            restTemplate.delete(requestUrl);
        } catch (HttpClientErrorException e) {
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
