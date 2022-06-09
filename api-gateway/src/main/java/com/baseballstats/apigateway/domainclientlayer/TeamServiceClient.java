package com.baseballstats.apigateway.domainclientlayer;

import com.baseballstats.apigateway.exceptions.HttpErrorInfo;
import com.baseballstats.apigateway.exceptions.InvalidInputException;
import com.baseballstats.apigateway.exceptions.NotFoundException;
import com.baseballstats.apigateway.mappinglayer.PlayerResponseModel;
import com.baseballstats.apigateway.mappinglayer.TeamDetailsResponseModel;
import com.baseballstats.apigateway.mappinglayer.TeamRequestModel;
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

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@Service
public class TeamServiceClient {
    private String url;

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    public TeamServiceClient(@Value("${app.teams.host}") String teamsServiceHost, @Value("${app.teams.port}") String teamsServicePort, ObjectMapper mapper, RestTemplate restTemplate){
        this.url = "http://" + teamsServiceHost + ":" + teamsServicePort + "/api";

        this.mapper = mapper;
        this.restTemplate = restTemplate;
    }

    public TeamDetailsResponseModel getTeamDetails(Integer teamId){
        TeamDetailsResponseModel responseModel;
        try{
            String requestUrl = url + "/teams/" + teamId;
            responseModel = restTemplate.getForObject(requestUrl, TeamDetailsResponseModel.class);
        }
        catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return responseModel;
    }

    public TeamDetailsResponseModel createTeam(TeamRequestModel teamRequestModel){
        TeamDetailsResponseModel responseModel;
        try{
            String requestUrl = url + "/teams";
            responseModel = restTemplate.postForObject(requestUrl, teamRequestModel, TeamDetailsResponseModel.class);
        }
        catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return responseModel;
    }

    public TeamDetailsResponseModel updateTeam(TeamRequestModel teamRequestModel, Integer teamId){
        TeamDetailsResponseModel responseModel;
        try{
            String requestUrl = url + "/teams/" + teamId;
            restTemplate.put(requestUrl, teamRequestModel);
            responseModel = restTemplate.getForObject(requestUrl, TeamDetailsResponseModel.class);
        }
        catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return responseModel;
    }

    public void deleteTeam(Integer teamId){
        try{
            String requestUrl = url + "/teams/" + teamId;
            restTemplate.delete(requestUrl);
        }
        catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
    }

    public List<TeamDetailsResponseModel> getTeamsByLeagueId(Integer leagueId){
        List<TeamDetailsResponseModel> responseModels;
        try{
            String requestUrl = url + "/leagues/" + leagueId + "/teams";
            responseModels = restTemplate.exchange(
                    requestUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TeamDetailsResponseModel>>() {
                    }).getBody();
        }
        catch (HttpClientErrorException e){
            throw handleHttpClientException(new HttpClientErrorException(e.getStatusCode()));
        }
        return responseModels;
    }

    public void deleteTeamsByLeagueId(Integer leagueId){
        try{
            String requestUrl = url + "/leagues/" + leagueId + "/teams";
            restTemplate.delete(requestUrl);
        }
        catch (HttpClientErrorException e){
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
