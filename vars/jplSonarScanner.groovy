def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    
    // name => Sonar Scaner Tool Name
    wrap ([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
        script {
            timeout(time: 1, unit: 'HOURS') {
                sh 'export'
                withSonarQubeEnv(sonarScannerToolName) {
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
