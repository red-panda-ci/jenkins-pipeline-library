#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library@v3.1.5') _

// Initialize global config
cfg = jplConfig('jpl','backend','', [email:'redpandaci+jpl@gmail.com'])

def publishDocumentation() {
    sh """
    make
    git add README.md vars/*.txt
    git commit -m 'Docs: Update README.md and Jenkins doc help files' || true
    git push -u origin ${env.BRANCH_NAME} || true
    """
}

pipeline {
    agent none

    stages {
        stage ('Initialize') {
            agent { label 'master' }
            steps  {
                jplStart(cfg)
            }
        }
        stage('Sonarqube Analysis') {
            when { expression { (env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-') } }
            agent { label 'master' }
            steps {
                jplSonarScanner(cfg)
            }
        }
        stage ('Test') {
            agent { label 'docker' }
            steps  {
                sh 'bin/test.sh'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'test/reports/**/*.*', fingerprint: true, allowEmptyArchive: true
                }
            }
        }
        // -------------------- automatic release -------------------
        stage ('Make release'){
            agent { label 'master' }
            when { branch 'release/new' }
            steps {
                publishDocumentation()
                jplMakeRelease(cfg, true)
            }
        }
        stage ('Release confirm') {
            when { expression { env.BRANCH_NAME.startsWith('release/v') || env.BRANCH_NAME.startsWith('hotfix/v') } }
            steps {
                jplPromoteBuild(cfg)
            }
        }
        stage ('Release finish') {
            agent { label 'master' }
            when { expression { ( env.BRANCH_NAME.startsWith('release/v') || env.BRANCH_NAME.startsWith('hotfix/v')) && cfg.promoteBuild.enabled } }
            steps {
                publishDocumentation()
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
        timeout(time: 1, unit: 'DAYS')
    }
}
