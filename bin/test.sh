#!/bin/bash

# Use "bin/test.sh local test [testname]"" to run only one specific test
# Example: "bin/test.sh local test jplStartTest"

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
    if [[ "$uniqueTestName" != "" ]] && [[ "$uniqueTestName" != "$testName" ]]
    then
        echo -e "\t\t(Mock)"
        return 0
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
testName=""
uniqueTestName=""
if [[ "$1" == "local" ]]
then
    timeoutSeconds=0
    containerPort="-p 8080:8080"
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
    timeoutSeconds=300
    containerPort=''
fi

# Main
echo -n "# Start jenkins as a time-boxed daemon container, running for max ${timeoutSeconds} seconds"
id=$(docker run ${containerPort} --rm -v jpl-dind-cache:/var/lib/docker -d --privileged redpandaci/jenkins-dind timeout ${timeoutSeconds} java -jar /usr/share/jenkins/jenkins.war)
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
runWithinDocker "git config --global push.default simple; git config --global user.email 'redpandaci@gmail.com'; git config --global user.name 'Red Panda CI'"
if [[ "$1" == "local" ]]
then
    echo "# Local test requested: Commit local jpl changes"
    runWithinDocker "cd /tmp/jenkins-pipeline-library; rm -f .git/hooks/*; git add -A; git commit -m 'test within docker' || true"
fi
runWithinDocker "cd /tmp/jenkins-pipeline-library; git rev-parse --verify develop || git checkout -b develop; git rev-parse --verify master || git checkout -b master; git checkout -b 'release/v9.9.9'; git checkout -b 'jpl-test'"

echo "# Wait for jenkins service to be initialized"
runWithinDocker "sleep 10; curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null"

echo "# Download jenkins cli"
runWithinDocker "sleep 2; wget http://localhost:8080/jnlpJars/jenkins-cli.jar -q > /dev/null"

# Run tests
if [[ ${doTests} == "true" ]]
then
    runTest "jplCheckoutSCMTest"
    runTest "jplStartTest"
    runTest "jplDockerPushTest"
    runTest "jplPromoteBuildTest" 4
    runTest "jplBuildAPKTest"
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
