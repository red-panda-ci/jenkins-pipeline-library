FROM teecke/jenkins-dind
USER jenkins
COPY --chown=jenkins:jenkins config /var/jenkins_home/
RUN install-plugins.sh < /var/jenkins_home/plugins.txt
USER root
RUN wget https://raw.githubusercontent.com/kairops/docker-command-launcher/master/kd.sh -O /usr/sbin/kd -q && \
    chmod +x /usr/sbin/kd && \
    curl https://raw.githubusercontent.com/teecke/devcontrol/master/install.sh | bash
RUN apk add --no-cache rsync openjdk8