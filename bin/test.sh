#!/bin/bash

# Use "bin/test.sh local test [testname]" to run only one specific test
# Example: "bin/test.sh local test jplStartTest"

# Functions
function runWithinDocker () {
    command=$1
    docker-compose exec -u jenkins -T jenkins-dind bash -c "${command}"
    returnValue=$((returnValue + $?))
}

function runTest () {
    testName=$1
    echo "# Run ${testName} Test..."
    if [[ "$2" == "" ]]
    then
        expectedResult=0
    else
        expectedResult=$2
    fi
    if [[ "$uniqueTestName" != "" ]] && [[ "$uniqueTestName" != "$testName" ]]
    then
        echo -e "\t\t(Force pass with assert=true)"
        return 0
    fi
    docker exec ${id} bash -c "ssh -o StrictHostKeyChecking=no -p 2222 localhost build ${testName} -s"
    if [[ "$?" -ne "${expectedResult}" ]]
    then
        returnValue=$((returnValue + 1))
    fi
    docker cp ${id}:/var/jenkins_home/jobs/${testName}/builds/1/log test/reports/${testName}.log
    returnValue=$((returnValue + $?))
}

# Configuration
cd "$(dirname $0)/.."
mkdir -p test/reports
rm -f test/reports/*
returnValue=0
doTests="false"
testName=""
uniqueTestName=""
if [[ "$1" == "local" ]]
then
    if [[ "$2" == "test" ]]
    then
        doTests="true"
        if [[ "$3" != "" ]]
        then
            uniqueTestName=$3
        fi
    fi
else
    doTests="true"
fi

# Main
echo "# Start jenkins as a docker-compose daemon"
docker-compose build --pull --no-cache
docker-compose up -d --force-recreate
returnValue=$((returnValue + $?))
id=$(docker-compose ps -q jenkins-dind)
returnValue=$((returnValue + $?))
echo "# Started platform with id ${id} and port $(docker-compose port jenkins-dind 8080)"

echo "# Prepare code for testing"
sleep 10
docker-compose exec -u jenkins -T jenkins-dind cp -Rp /opt/jpl-source/ /tmp/jenkins-pipeline-library/
docker-compose exec -u jenkins -T jenkins-agent1 cp -Rp /opt/jpl-source/ /tmp/jenkins-pipeline-library/
docker-compose exec -u jenkins -T jenkins-agent2 cp -Rp /opt/jpl-source/ /tmp/jenkins-pipeline-library/
runWithinDocker "rm -f /tmp/jenkins-pipeline-library/.git/hooks/* && git config --global push.default simple && git config --global user.email 'redpandaci@gmail.com' && git config --global user.name 'Red Panda CI'"
if [[ "$1" == "local" ]] && [[ "$(git status --porcelain)" != "" ]]
then
    echo "# Local test requested: Commit local jpl changes"
    runWithinDocker "cd /tmp/jenkins-pipeline-library && git add -A && git commit -m 'test within docker'"
fi
runWithinDocker "cd /tmp/jenkins-pipeline-library && git rev-parse --verify develop || git checkout -b develop"
runWithinDocker "cd /tmp/jenkins-pipeline-library && git rev-parse --verify master || git checkout -b master"
runWithinDocker "cd /tmp/jenkins-pipeline-library && git branch -D release/new || true"
runWithinDocker "cd /tmp/jenkins-pipeline-library && git checkout -b 'release/v9.9.9' && git checkout -b 'hotfix/v9.9.9-hotfix-1' && git checkout -b 'jpl-test-promoted' && git checkout -b 'jpl-test' && git checkout -b 'release/new' && git checkout `git rev-parse HEAD` > /dev/null 2>&1"

echo "# Waiting for jenkins service to be initialized"
runWithinDocker "sleep 10 && curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null; sleep 10"

echo "# Prepare agents"
for agent in agent1 agent2
do
    secret=$(docker-compose exec -T jenkins-dind /opt/jpl-source/bin/prepare_agent.sh ${agent})
    docker-compose exec -u jenkins -d -T jenkins-${agent} jenkins-slave -url http://jenkins-dind:8080 ${secret} ${agent}
done

echo "# Reload Jenkins configuration"
runWithinDocker "ssh -o StrictHostKeyChecking=no -p 2222 localhost reload-configuration"

# Run tests
if [[ ${doTests} == "true" ]]
then
    runTest "jplCheckoutSCMTest"
    runTest "jplStartTest"
    runTest "jplGitCacheHappyTest"
    runTest "jplDockerBuildTest"
    runTest "jplDockerPushTest"
    runTest "jplPromoteCodeHappyTest"
    runTest "jplPromoteBuildTest" 4
    [ "$1" != "local" ] && runTest "jplBuildAPKTest"
    runTest "jplBuildIPAHappyTest"
    runTest "jplMakeReleaseHappyTest"
    runTest "jplCloseReleaseTest"
    runTest "jplCloseHotfixHappyTest"
fi

# Remove compose
if [[ "$1" != "local" ]]
then
    echo "# Switch down and clear compose"
    docker-compose down -v
    returnValue=$((returnValue + $?))
fi

exit ${returnValue}
