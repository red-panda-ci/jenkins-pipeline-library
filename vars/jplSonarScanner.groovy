def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    
    // name => Sonar Scaner Tool Name
    wrap ([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
        script {
            timeout(time: 1, unit: 'HOURS') {
                def sonarHome = tool 'SonarQube Scanner 2.8';
                withSonarQubeEnv(config.sonarScannerToolName) {
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
