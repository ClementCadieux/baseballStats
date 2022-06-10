#!/usr/bin/env bash

spring init \
--boot-version=2.6.3.RELEASE \
--build=gradle \
--java-version=1.8 \
--packaging=jar \
--name=stats-service \
--package-name=com.baseballstats.stat \
--groupId=com.baseballstats.stat \
--dependencies=web \
--version=1.0.0-SNAPSHOT \
stats-service
