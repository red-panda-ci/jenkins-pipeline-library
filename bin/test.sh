#!/bin/bash

# Use "bin/test.sh local test [testname]"" to run only one specific test
# Example: "bin/test.sh local test jplStartTest"

# Functions
function runWithinDocker () {
    command=$1
    docker-compose exec jenkins-dind bash -c "${command}"
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
        echo -e "\t\t(Mock)"
        return 0
    fi
    docker-compose exec jenkins-dind bash -c "java -jar jenkins-cli.jar -s http://localhost:8080 build ${testName} --username redpanda --password redpanda -s"
    if [[ "$?" -ne "${expectedResult}" ]]
    then
        returnValue=$((returnValue + 1))
    fi
    docker cp ${id}:/root/.jenkins/jobs/${testName}/builds/1/log test/reports/${testName}.log
    returnValue=$((returnValue + $?))
}

# Configuration
cd $(dirname "$0")/..
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
echo -n "# Start jenkins as a docker-compose daemon"
docker volume create jpl-dind-cache
returnValue=$((returnValue + $?))
docker-compose up -d
returnValue=$((returnValue + $?))
id=$(docker-compose ps -q jenkins-dind)
returnValue=$((returnValue + $?))
echo " with id ${id} and port $(docker-compose port jenkins-dind 8080)"

echo "# Copy jenkins configuration"
docker cp test/config/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml ${id}:/root/.jenkins
returnValue=$((returnValue + $?))
docker cp test/jobs/ ${id}:/root/.jenkins/jobs/
returnValue=$((returnValue + $?))

echo "# Preparing jpl code for testing"
docker cp `pwd` ${id}:/tmp/jenkins-pipeline-library
returnValue=$((returnValue + $?))
runWithinDocker "rm -f /tmp/jenkins-pipeline-library/.git/hooks/* && git config --global push.default simple && git config --global user.email 'redpandaci@gmail.com' && git config --global user.name 'Red Panda CI'"
if [[ "$1" == "local" ]] && [[ "$(git status --porcelain)" != "" ]]
then
    echo "# Local test requested: Commit local jpl changes"
    runWithinDocker "cd /tmp/jenkins-pipeline-library && git add -A && git commit -m 'test within docker'"
fi
runWithinDocker "cd /tmp/jenkins-pipeline-library && git rev-parse --verify develop || git checkout -b develop"
runWithinDocker "cd /tmp/jenkins-pipeline-library && git rev-parse --verify master || git checkout -b master"
runWithinDocker "cd /tmp/jenkins-pipeline-library && git checkout -b 'release/v9.9.9' && git checkout -b 'jpl-test-promoted' && git checkout -b 'jpl-test' && git checkout `git rev-parse HEAD` > /dev/null 2>&1"

echo "# Wait for jenkins service to be initialized"
runWithinDocker "sleep 10 && curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null"

echo "# Download jenkins cli"
runWithinDocker "sleep 2 && wget http://localhost:8080/jnlpJars/jenkins-cli.jar -q > /dev/null"

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
    runTest "jplCloseReleaseTest"
fi

# Remove compose
if [[ "$1" != "local" ]]
then
    echo "# Switch down and clear compose"
    docker-compose down
    returnValue=$((returnValue + $?))
fi

exit ${returnValue}
