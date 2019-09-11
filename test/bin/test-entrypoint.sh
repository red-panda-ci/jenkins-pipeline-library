#!/bin/bash
mkdir -p /var/jenkins_home/.jenkins
install-plugins.sh < /opt/jpl-source/test/plugins.txt 
cp -p /opt/jpl-source/test/jenkins_home/config.xml /var/jenkins_home/.jenkins
cp -p /opt/jpl-source/test/config/org.jenkinsci.plugins.workflow.libs.GlobalLibraries.xml /var/jenkins_home/.jenkins
cp -Rp /opt/jpl-source/test/jobs/ /var/jenkins_home/.jenkins/jobs/
chown jenkins:jenkins /var/jenkins_home -R
/sbin/tini -- /usr/local/bin/teecke-jenkins-dind.sh
