def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    
    steps {
        echo "Into the script!!!! Great"
    }
/*
    pipeline {
        agent any
        stages {
            stage('Sonarqube Analysis') {
                agent any
                steps {
                    wrap ([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
                        script {
                            timeout(time: 1, unit: 'HOURS') {
                                sh 'export'
                                withSonarQubeEnv('SonarQube') {
                                    sh 'export'
                                    sh "${sonarHome}/bin/sonar-scanner"
                                }
                                def qg = waitForQualityGate()
                                if (qg.status != 'OK') {
                                    error "Pipeline aborted due to quality gate failure: ${qg.status}"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    */
}
