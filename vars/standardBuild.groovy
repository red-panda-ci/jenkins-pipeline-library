def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    pipeline {
        agent any
        stages {
            stage ("main") {
                agent any
                when { expression { (env.BRANCH_NAME in ['develop','staging','quality','master'] || env.BRANCH_NAME.startsWith('PR-')) ? true : false } }
                steps  {
                    echo "Test!"
                }
            }
        }
    }
    /*
    node {
        stage('Checkout') {
            checkout scm
        }
        stage('Main') {
            docker.image(config.environment).inside {
                sh config.mainScript
            }
        }
        stage('Post') {
            sh config.postScript
        }
    }
*/
}
