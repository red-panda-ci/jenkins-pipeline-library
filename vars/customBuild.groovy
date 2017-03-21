def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    pipeline {
        agent any
        stages {
            stage('Sonarqube Analysis') {
                agent any
                when { expression { ((env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-')) ? true : false } }
                steps {
                    wrap ([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
                        script {
                            timeout(time: 1, unit: 'HOURS') {
                                withSonarQubeEnv('SonarQube') {
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
}
