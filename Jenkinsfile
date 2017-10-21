#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library@develop') _

// Initialize global config
cfg = jplConfig('jpl','backend','', [hipchat: '', slack: '#integrations', email:'redpandaci+jpl@gmail.com'])

pipeline {
    agent none

    stages {
        stage ('Build') {
            agent { label 'docker' }
            steps  {
                jplCheckoutSCM(cfg)
            }
        }
        stage ('Test') {
            agent { label 'docker' }
            steps  {
                timestamps {
                    ansiColor('xterm') {
                        sh 'bin/test.sh'
                    }
                }
            }
        }
        stage('Sonarqube Analysis') {
            agent { label 'docker' }
            steps {
                jplSonarScanner(cfg)
            }
        }
        stage ('Release confirm') {
            when { branch 'release/*' }
            steps {
                jplPromoteBuild(cfg)
            }
        }
        stage ('Release finish') {
            agent { label 'docker' }
            when { branch 'release/*' }
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
