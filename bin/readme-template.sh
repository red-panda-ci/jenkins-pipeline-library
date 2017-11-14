#!/bin/bash

cd $(dirname "$0")/..

DOCUMENTATION="$(cd vars
for file in jpl*.groovy 
do
    title=`echo -n ${file}|cut -f 1 -d "."`
    echo "### ${title}"
    sed "/def call/Q" ${file}|sed "s/\/\*\*//g"|sed "s/\*\///g" |tee ${title}.txt
done
)"

cat << EOF
# Jenkins Pipeline Library

[![Build Status](http://jenkins.redpandaci.com/buildStatus/icon?job=red-panda-ci/jenkins-pipeline-library/develop)](https://jenkins.redpandaci.com/job/red-panda-ci/job/jenkins-pipeline-library/job/develop/)

## Description

Library with a set of helpers to be used in Jenkins Scripted or Declarative Pipelines

This helpers are designed to be used in "Multibranch Pipeline" Jenkins job type, with "git flow" release cycle and at least with the following branches:

* develop
* master

## Usage

Add this line at the top of your Jenkinsfile

    @Library('github.com/red-panda-ci/jenkins-pipeline-library') _

Then you can use the helpers in your script

* Scripted Pipeline Example

TBD

* Declarative Pipeline example (Android)

\`\`\`groovy
#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library') _

// Initialize cfg
cfg = jplConfig('project-alias', 'android', 'JIRAPROJECTKEY', [hipchat:'The-Project,Jenkins QA', slack:'#the-project,#integrations', email:'the-project@example.com,dev-team@example.com,qa-team@example.com'])

// The pipeline
pipeline {

    agent none

    stages {
        stage ('Initialize') {
            agent { label 'docker' }
            steps  {
                jplStart(cfg)
            }
        }
        stage ('Docker push') {
            agent { label 'docker' }
            steps  {
                jplDockerPush(cfg, 'the-project/docker-image', 'https://registry.hub.docker.com', 'dockerhub-credentials', 'dockerfile-path')
            }
        }
        stage ('Build') {
            agent { label 'docker' }
            steps  {
                jplBuild(cfg)
            }
        }
        stage('Test') {
            agent { label 'docker' }
            when { expression { (env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-') } }
            steps {
                jplSonarScanner(cfg)
            }
        }
        stage ('Sign') {
            agent { label 'docker' }
            when { branch 'release/v*' }
            steps  {
                jplSigning(cfg, "git@github.org:the-project/sign-repsotory.git", "the-project", "app/build/outputs/apk/the-project-unsigned.apk")
                archiveArtifacts artifacts: "**/*-signed.apk", fingerprint: true, allowEmptyArchive: false
            }
        }
        stage ('Release confirm') {
            when { branch 'release/v*' }
            steps {
                jplPromoteBuild(cfg)
            }
        }
        stage ('Release finish') {
            agent { label 'docker' }
            when { branch 'release/v*' }
            steps {
                jplCloseRelease(cfg)
            }
        }
        stage ('PR Clean') {
            agent { label 'docker' }
            when { branch 'PR-*' }
            steps {
                deleteDir()
            }
        }
    }

    post {
        always {
            jplPostBuild(cfg)
        }
    }

    options {
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(artifactNumToKeepStr: '20',artifactDaysToKeepStr: '30'))
        disableConcurrentBuilds()
        skipDefaultCheckout()
        timeout(time: 1, unit: 'DAYS')
    }
}
\`\`\`

## Helpers set

$DOCUMENTATION

## Dependencies

You should consider the following configurations:

### Jenkins service

* Install Java "jre" and "jdk"
\`\`\`console
$ apt-get install default-jre default-jdk
[...]
\`\`\`
* Configure "git" to be able to make push to the repositories.
  * Configure git credentials (username, password) <https://git-scm.com/docs/git-credential-store> for use with "https" remote repositories and set "store" as global config option, with global user name and global email
    \`\`\`console
    $ git config --global credential.helper store
    $ git config --global user.name "Red Panda"
    $ git config --global user.email "redpandaci@gmail.com"
    $ cat .gitconfig
    [user]
        email = redpandaci@gmail.com
        name = Red Panda
    [push]
        default = simple
    [credential]
        helper = store
    $ cat ~/.git-credentials
    https://redpandaci%40gmail.com:fake-password@github.com
    \`\`\`
  * Configure ssh public key for use with "ssh" remote repositories.
  \`\`\`console
    $ ssh-keygen
    Generating public/private rsa key pair.
    Enter file in which to save the key (/var/lib/jenkins/.ssh/id_rsa): 
    Enter passphrase (empty for no passphrase):
    Enter same passphrase again:
    Your identification has been saved in /var/lib/jenkins/.ssh/id_rsa.
    Your public key has been saved in /var/lib/jenkins/.ssh/id_rsa.pub.
    The key fingerprint is:
    SHA256:+IKayZj7m06twLgUSWyb2jLINPjzp6uQeeyA8nmYjqk server@jenkins
    The key's randomart image is:
    +---[RSA 2048]----+
    |                 |
    |.                |
    | +               |
    |+ +    .         |
    |.B    . S        |
    |O*o. . .         |
    |#+Boo . .        |
    |o^oX. ..         |
    |E=^+++           |
    +----[SHA256]-----+
    jenkins@server:~$ cat .ssh/id_rsa.pub
    ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC72EdmruDtEoqF3BK7JPjgVGMfL7hnPVymdUEt76gk1U/sSaYsijbqxyhSbdp/8W7l1dwGA1Vs7cAn15qVzbUoJzmmM1rm7wPOBU7oBH1//oopA5U1XauXRuKWFQ8LDbjdaHBriBP4IyIG9fS+afgRwDlwlxx2mKuWhuYlHbBAxGwwDpxtTnvJ9JAnWG5eJ+8cXJ2PaIBlhc8jkjWkvLOnAWx729LdFQqWrikY5YwtNKw0CnU5XGBP96GcyR+k7PPkdr8LcVCewE042n6pw43e3H4GRlWU2w/nj/JniF6Tyx76hxSX9UMFiCKVXqM8blftqn9H7WGStt0b1pPhwtGT server@jenkins
  \`\`\`
* Install docker and enable Jenkins syste user to use the docker daemon.
* Install this plugins:
  * AnsiColor
  * Bitbucket Branch Source
  * Bitbucket Plugin
  * Blue Ocean
  * Copy Artifact Plugin
  * File Operations
  * Github Branch Source
  * Github Plugin
  * Git Plugin
  * HipChat, if you want to use hipchat as notification channel
  * HTML Publisher
  * JIRA Pipeline Steps, if you want to use a JIRA project
  * Pipeline
  * Pipeline Utility Steps, if you want to sign android APK's artifacts with jplSigning
  * Slack Notification, if you want to use Slack as notification channel
  * SonarQube Scanner, if you want to use SonerQube as quality gate with jplSonarScanner
  * Timestamper
* Setup Jeknins in "Configuration" main menu option
  * Enable the checkbox "Environment Variables" and add the following environment variables with each integration key:
    * APPETIZE_TOKEN
    * APPLIVERY_TOKEN
    * APPETIZE_TOKEN
    * JIRA_SITE
  * Put the correct Slack, Hipchat and JIRA credentials in their place (read the howto's of the related Jenkins plugins)
EOF
