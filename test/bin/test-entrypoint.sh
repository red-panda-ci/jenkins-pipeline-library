#!/bin/bash
mkdir -p /var/jenkins_home/.jenkins
cp /tmp/jpl-source/test/jenkins_home/config.xml /var/jenkins_home/.jenkins
chown jenkins:jenkins /var/jenkins_home -R
/sbin/tini -- /usr/local/bin/teecke-jenkins-dind.sh
