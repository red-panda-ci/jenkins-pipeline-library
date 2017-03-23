def call(String sonarScannerToolName = "SonarQube") {
    wrap ([$class: 'AnsiColorBuildWrapper', 'colorMapName': 'XTerm']) {
        script {
            timeout(time: 1, unit: 'HOURS') {
                def sonarHome = tool sonarScannerToolName;
                withSonarQubeEnv(sonarScannerToolName) {
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
