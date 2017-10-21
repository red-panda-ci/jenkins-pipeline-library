#!/bin/bash
cd $(dirname "$0")/..

RETURN_VALUE=0
TIMEBOX_SECONDS=3000

echo -n "# Start jenkins as a time-boxed daemon container, running for max ${TIMEBOX_SECONDS} seconds"
id=$(docker run -p 8080:8080 --rm -v jpl-dind-cache:/var/lib/docker -v `pwd`:/tmp/jenkins-pipeline-library -d --privileged redpandaci/jenkins-dind timeout ${TIMEBOX_SECONDS} /usr/bin/supervisord)
RETURN_VALUE=$((RETURN_VALUE + $?))
echo " with id ${id}"

echo "# Copy jenkins configuration"
docker cp test/config/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml ${id}:/root/.jenkins
RETURN_VALUE=$((RETURN_VALUE + $?))
docker cp test/jobs/ ${id}:/root/.jenkins/jobs/
RETURN_VALUE=$((RETURN_VALUE + $?))

echo "# Wait for jenkins service to be initialized"
sleep 10
docker exec ${id} curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null
RETURN_VALUE=$((RETURN_VALUE + $?))

echo "# Download jenkins cli"
docker exec ${id} wget http://localhost:8080/jnlpJars/jenkins-cli.jar -q > /dev/null
RETURN_VALUE=$((RETURN_VALUE + $?))

echo "# Run jplCheckoutSCM Test..."
docker exec ${id} java -jar jenkins-cli.jar -s http://localhost:8080 build jplCheckoutSCM --username redpanda --password redpanda -s
RETURN_VALUE=$((RETURN_VALUE + $?))
echo "# Run jplDocker Test..."
docker exec ${id} java -jar jenkins-cli.jar -s http://localhost:8080 build jplDocker --username redpanda --password redpanda -s
RETURN_VALUE=$((RETURN_VALUE + $?))

echo "# Stop jenkins daemon container"
#docker rm -f ${id}
RETURN_VALUE=$((RETURN_VALUE + $?))

exit ${RETURN_VALUE}

