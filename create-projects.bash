#!/usr/bin/env bash

spring init \
--boot-version=2.6.3.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=player-service \
--package-name=com.baseballstats.player \
--groupId=com.baseballstats.player \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
player-service

spring init \
--boot-version=2.6.3.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=team-service \
--package-name=com.baseballstats.team \
--groupId=com.baseballstats.team \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
team-service

spring init \
--boot-version=2.6.3.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=league-service \
--package-name=com.baseballstats.league \
--groupId=com.baseballstats.league \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
league-service

spring init \
--boot-version=2.6.3.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=api-gateway \
--package-name=com.movielister.apigateway \
--groupId=com.movielister.apigateway \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
api-gateway

