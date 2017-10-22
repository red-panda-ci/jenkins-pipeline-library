#!/bin/bash

# Functions
function runWithinDocker () {
    command=$1
    docker exec ${id} bash -c "${command}"
    RETURN_VALUE=$((RETURN_VALUE + $?))
}

function runTest () {
    echo "# Run ${testName} Test..."
    testName=$1
    runWithinDocker "java -jar jenkins-cli.jar -s http://localhost:8080 build ${testName} --username redpanda --password redpanda -s"
    docker cp ${id}:/root/.jenkins/jobs/jplCheckoutSCM/builds/1/log test/reports/${testName}.log
    RETURN_VALUE=$((RETURN_VALUE + $?))
}

# Configuration
cd $(dirname "$0")/..
mkdir -p test/reports
rm -f test/reports/*
RETURN_VALUE=0
TIMEBOX_SECONDS=300

# Main
echo -n "# Start jenkins as a time-boxed daemon container, running for max ${TIMEBOX_SECONDS} seconds"
id=$(docker run --rm -v jpl-dind-cache:/var/lib/docker -d --privileged redpandaci/jenkins-dind timeout ${TIMEBOX_SECONDS} /usr/bin/supervisord)
RETURN_VALUE=$((RETURN_VALUE + $?))
echo " with id ${id}"

echo "# Copy jenkins configuration"
docker cp test/config/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml ${id}:/root/.jenkins
RETURN_VALUE=$((RETURN_VALUE + $?))
docker cp test/jobs/ ${id}:/root/.jenkins/jobs/
RETURN_VALUE=$((RETURN_VALUE + $?))

echo "# Preparing jpl code for testing"
docker cp `pwd` ${id}:/tmp/jenkins-pipeline-library
RETURN_VALUE=$((RETURN_VALUE + $?))
runWithinDocker "cd /tmp/jenkins-pipeline-library; git checkout -b 'jpl-test'"

if [[ $1 == 'local' ]]
then
    echo "# Local test requested: Commit local jpl changes"
    runWithinDocker "git config --global user.email 'redpandaci@gmail.com'; git config --global user.name 'Red Panda CI'; cd /tmp/jenkins-pipeline-library; rm -f .git/hooks/*; git commit -am 'test within docker'"
fi

echo "# Wait for jenkins service to be initialized"
runWithinDocker "sleep 10; curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null"

echo "# Download jenkins cli"
runWithinDocker "sleep 2; wget http://localhost:8080/jnlpJars/jenkins-cli.jar -q > /dev/null"

# Run tests
runTest "jplCheckoutSCM"
runTest "jplDockerPush"

# Remove container
if [[ $1 != 'local' ]]
then
    echo "# Stop jenkins daemon container"
    docker rm -f ${id}
    RETURN_VALUE=$((RETURN_VALUE + $?))
fi

exit ${RETURN_VALUE}
