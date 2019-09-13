FROM teecke/jenkins-dind
USER jenkins
COPY --chown=jenkins:jenkins config /var/jenkins_home/
RUN install-plugins.sh < /var/jenkins_home/plugins.txt 
USER root
