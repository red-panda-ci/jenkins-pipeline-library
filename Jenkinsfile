#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library') _

// Initialize global config
cfg = jplConfig('jpl','backend','', [slack: '#integrations', email:'redpandaci+jpl@gmail.com'])

pipeline {
    agent none

    stages {
        stage ('Initialize') {
            agent { label 'master' }
            steps  {
                jplStart(cfg)
            }
        }
        stage ('Test') {
            agent { label 'docker' }
            when { expression { (env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-') || env.BRANCH_NAME.startsWith('feature/') } }
            steps  {
                // Temporary disabled on 2019-05-15
                sh 'echo "Disable test: bin/test.sh"'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'test/reports/*', fingerprint: true, allowEmptyArchive: false
                }
            }
        }
        stage('Sonarqube Analysis') {
            when { expression { (env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-') } }
            agent { label 'docker' }
            steps {
                jplSonarScanner(cfg)
            }
        }
        stage ('Release confirm') {
            when { expression { env.BRANCH_NAME.startsWith('release/v') || env.BRANCH_NAME.startsWith('hotfix/v') } }
            steps {
                jplPromoteBuild(cfg)
            }
        }
        stage ('Release finish') {
            agent { label 'docker' }
            when { expression { ( env.BRANCH_NAME.startsWith('release/v') || env.BRANCH_NAME.startsWith('hotfix/v')) && cfg.promoteBuild.enabled } }
            steps {
                sh "make; git add README.md vars/*.txt; git commit -m 'Docs: Update README.md and Jenkins doc help files' || true"
                sh "git push -u origin ${env.BRANCH_NAME} || true"
                jplCloseRelease(cfg)
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
