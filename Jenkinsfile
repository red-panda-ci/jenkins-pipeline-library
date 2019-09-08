#!groovy

@Library('github.com/teecke/jenkins-pipeline-library@v3.3.0') _

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
            agent { label 'docker' }
            steps  {
                jplStart(cfg)
            }
        }
        stage('Sonarqube Analysis') {
            when { expression { (env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-') } }
            agent { label 'docker' }
            steps {
                echo "Temporary disabled"
                //jplSonarScanner(cfg)
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
        stage ('Make release'){
            agent { label 'docker' }
            when { branch 'release/new' }
            steps {
                publishDocumentation()
                jplMakeRelease(cfg, true)
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
        timeout(time: 30, unit: 'MINUTES')
    }
}
