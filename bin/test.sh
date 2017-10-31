#!/bin/bash

# Functions
function runWithinDocker () {
    command=$1
    docker exec ${id} bash -c "${command}"
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
    docker exec ${id} bash -c "java -jar jenkins-cli.jar -s http://localhost:8080 build ${testName} --username redpanda --password redpanda -s"
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
if [[ "$1" == "local" ]]
then
    timeoutSeconds=0
    containerPort="-p 8080:8080"
    if [[ "$2" == "test" ]]
    then
        doTests="true"
    fi
else
    doTests="true"
    timeoutSeconds=300
    containerPort=''
fi

# Main
echo -n "# Start jenkins as a time-boxed daemon container, running for max ${timeoutSeconds} seconds"
id=$(docker run ${containerPort} --rm -v jpl-dind-cache:/var/lib/docker -d --privileged redpandaci/jenkins-dind timeout ${timeoutSeconds} /usr/bin/supervisord)
returnValue=$((returnValue + $?))
echo " with id ${id}"

echo "# Copy jenkins configuration"
docker cp test/config/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml ${id}:/root/.jenkins
returnValue=$((returnValue + $?))
docker cp test/jobs/ ${id}:/root/.jenkins/jobs/
returnValue=$((returnValue + $?))

echo "# Preparing jpl code for testing"
docker cp `pwd` ${id}:/tmp/jenkins-pipeline-library
returnValue=$((returnValue + $?))
if [[ "$1" == "local" ]]
then
    echo "# Local test requested: Commit local jpl changes"
    runWithinDocker "git config --global user.email 'redpandaci@gmail.com'; git config --global user.name 'Red Panda CI'; cd /tmp/jenkins-pipeline-library; rm -f .git/hooks/*; git add -A; git commit -m 'test within docker'"
fi
runWithinDocker "cd /tmp/jenkins-pipeline-library; grep '\+refs/heads/\*:refs/remotes/origin/\*' .git/config -q || git config --add remote.origin.fetch +refs/heads/*:refs/remotes/origin/*"
runWithinDocker "cd /tmp/jenkins-pipeline-library; git checkout -b 'release/v9.9.9'; git checkout -b 'jpl-test'"


echo "# Wait for jenkins service to be initialized"
runWithinDocker "sleep 10; curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null"

echo "# Download jenkins cli"
runWithinDocker "sleep 2; wget http://localhost:8080/jnlpJars/jenkins-cli.jar -q > /dev/null"

# Run tests
if [[ ${doTests} == "true" ]]
then
    runTest "jplCheckoutSCMTest"
    runTest "jplDockerPush"
    runTest "jplPromoteBuildTest" 4
    runTest "jplCloseReleaseTest"
fi

# Remove container
if [[ "$1" != "local" ]]
then
    echo "# Stop jenkins daemon container"
    docker rm -f ${id}
    returnValue=$((returnValue + $?))
fi

exit ${returnValue}
