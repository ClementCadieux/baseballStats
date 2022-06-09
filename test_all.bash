#!/usr/bin/env bash
#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

#array to hold all our test data movie ids
allTestLeagueIds=()

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {

  body=\
'{"name":"First League", "category": "A", "teamSummaryModels":[{"name":"Tigers", "city": "Tokyo"}, {"name":"Dragons", "city": "Beijing"}]}'
    recreateAggregate 1 "$body"

body=\
'{"name":"Second League", "category": "AA", "teamSummaryModels":[{"name":"Panthers", "city": "Seoul"}]}'
    recreateAggregate 2 "$body"

body=\
'{"name":"Third League", "category": "AAA", "teamSummaryModels":[]}'
    recreateAggregate 3 "$body"
}

function recreateAggregate() {
    local testId=$1
    local aggregate=$2

    leagueId=$(curl -X POST http://$HOST:$PORT/api/leagues -H "Content-Type:
    application/json" --data "$aggregate" | jq '.leagueId')
    allTestLeagueIds[$testId]=$leagueId
    echo "Added League Aggregate with leagueId: ${allTestLeagueIds[$testId]}"
}

#don't start testing until all the microservices are up and running
function waitForService() {
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi

#waitForService http://$HOST:${PORT}/api/movies/423
#try to delete a movie that you've set up but that you don't need. This will confirm that things are working
#waitForService curl -X DELETE http://$HOST:$PORT/api/movies/423

setupTestdata

#Explicit GET Leagye Aggregate tests

# Verify that a normal request works, expect two Teams
assertCurl 200 "curl http://$HOST:$PORT/api/leagues/${allTestLeagueIds[1]} -s"
assertEqual ${allTestLeagueIds[1]} $(echo $RESPONSE | jq .leagueId)
assertEqual 2 $(echo $RESPONSE | jq ".teamSummaryModels | length")

# Verify that a normal request works, expect one Teams

assertCurl 200 "curl http://$HOST:$PORT/api/leagues/${allTestLeagueIds[2]} -s"
assertEqual ${allTestLeagueIds[2]} $(echo $RESPONSE | jq .leagueId)
assertEqual 1 $(echo $RESPONSE | jq ".teamSummaryModels | length")

# Verify that a normal request works, expect no Teams
assertCurl 200 "curl http://$HOST:$PORT/api/leagues/${allTestLeagueIds[3]} -s"
assertEqual ${allTestLeagueIds[3]} $(echo $RESPONSE | jq .leagueId)
assertEqual 0 $(echo $RESPONSE | jq ".teamSummaryModels | length")

# Verify that a 404 (Not Found) error is returned for a non existing bankId (99)
assertCurl 404 "curl http://$HOST:$PORT/api/leagues/99 -s"

# Verify that a 422 (Unprocessable Entity) error is returned for a bankId that is out of range (-1)
assertCurl 422 "curl http://$HOST:$PORT/api/leagues/-1 -s"

# Verify that a 400 (Bad Request) error error is returned for a bankId that is not a number, i.e. invalid format
assertCurl 400 "curl http://$HOST:$PORT/api/leagues/ffvdfhj -s"

#Explicit tests for DELETE movie aggregate


#Verify that a normal delete works, use Bank Aggregate from TestData 1 i.e. allTestMovieIds[1]
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/leagues/${allTestLeagueIds[1]} -s"



if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi