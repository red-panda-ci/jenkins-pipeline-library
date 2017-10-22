#!/bin/bash

function runTestWithinJenkins () {
    testName=$1
    echo "# Run ${testName} Test..."
    docker exec ${id} java -jar jenkins-cli.jar -s http://localhost:8080 build ${testName} --username redpanda --password redpanda -s
    docker cp ${id}:/root/.jenkins/jobs/jplCheckoutSCM/builds/1/log test/reports/${testName}.log
    RETURN_VALUE=$((RETURN_VALUE + $?))
}

cd $(dirname "$0")/..
mkdir -p test/reports
rm -f test/reports/*

RETURN_VALUE=0
TIMEBOX_SECONDS=300

echo -n "# Start jenkins as a time-boxed daemon container, running for max ${TIMEBOX_SECONDS} seconds"
#id=$(docker run --rm -v jpl-dind-cache:/var/lib/docker -v `pwd`:/tmp/jenkins-pipeline-library -d --privileged redpandaci/jenkins-dind timeout ${TIMEBOX_SECONDS} /usr/bin/supervisord)
id=$(docker run --rm -v jpl-dind-cache:/var/lib/docker -d --privileged redpandaci/jenkins-dind timeout ${TIMEBOX_SECONDS} /usr/bin/supervisord)
RETURN_VALUE=$((RETURN_VALUE + $?))
echo " with id ${id}"

echo "# Copy jenkins configuration"
docker cp test/config/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml ${id}:/root/.jenkins
RETURN_VALUE=$((RETURN_VALUE + $?))
docker cp test/jobs/ ${id}:/root/.jenkins/jobs/
RETURN_VALUE=$((RETURN_VALUE + $?))
docker cp `pwd` ${id}:/tmp/jenkins-pipeline-library

echo "# Preparing jpl for test"
docker exec ${id} bash -c "cd /tmp/jenkins-pipeline-library; git checkout -b 'jpl-test'"
if [[ $1 == 'local' ]]
then
    echo "# Local test requested: Commit local jpl changes"
    docker exec ${id} bash -c "git config --global user.email 'redpandaci@gmail.com'; git config --global user.name 'Red Panda CI'; cd /tmp/jenkins-pipeline-library; rm -f .git/hooks/*; git commit -am 'test within docker'"
fi

echo "# Wait for jenkins service to be initialized"
sleep 10
docker exec ${id} curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null
RETURN_VALUE=$((RETURN_VALUE + $?))

echo "# Download jenkins cli"
docker exec ${id} wget http://localhost:8080/jnlpJars/jenkins-cli.jar -q > /dev/null
RETURN_VALUE=$((RETURN_VALUE + $?))

# Run tests
runTestWithinJenkins "jplCheckoutSCM"
runTestWithinJenkins "jplDockerPush"

echo "# Stop jenkins daemon container"
#docker rm -f ${id}
RETURN_VALUE=$((RETURN_VALUE + $?))

exit ${RETURN_VALUE}

