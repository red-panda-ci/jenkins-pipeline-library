#!/bin/bash
cd $(dirname "$0")/..

echo -n "# Start jenkins as a daemon container"
id=$(docker run -v jpl-dind-cache:/var/lib/docker -v `pwd`:/tmp/jenkins-pipeline-library -d --privileged redpandaci/jenkins-dind)
echo " with id ${id}"

echo "# Copy jenkins configuration"
docker cp test/config/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml ${id}:/root/.jenkins
docker cp test/jobs/ ${id}:/root/.jenkins/jobs/

echo "# Wait for jenkins service to be initialized"
sleep 10
docker exec ${id} curl --max-time 50 --retry 10 --retry-delay 5 --retry-max-time 32 http://localhost:8080 -s > /dev/null

echo "# Download jenkins cli"
docker exec ${id} wget http://localhost:8080/jnlpJars/jenkins-cli.jar -q > /dev/null

echo "# Execute the test"
docker exec ${id} java -jar jenkins-cli.jar -s http://localhost:8080 build jplCheckoutSCM --username redpanda --password redpanda -s

echo "# Stop jenkins daemon container"
docker rm -f ${id}
