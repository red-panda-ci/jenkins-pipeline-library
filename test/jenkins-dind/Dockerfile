FROM teecke/jenkins-dind:2.190.1
RUN apk add --no-cache rsync openjdk8 jq
USER jenkins
COPY --chown=jenkins:jenkins config /var/jenkins_home/
RUN install-plugins.sh < /var/jenkins_home/plugins.txt
USER root
RUN wget https://raw.githubusercontent.com/kairops/docker-command-launcher/master/kd.sh -O /usr/sbin/kd -q && \
    chmod +x /usr/sbin/kd && \
    curl https://raw.githubusercontent.com/teecke/devcontrol/master/install.sh | bash
