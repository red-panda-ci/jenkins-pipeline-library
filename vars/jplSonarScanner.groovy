/**

  Launch SonarQube scanner

  Parameters:
  * String sonarScanerToolName The name of the SonarQube tool name configured in your Jenkins installation
  * Boolean abortIfQualityGateFails Abort the job with error result. You must have a webhook configured in SonarQube to your Jenkins

*/
def call(String sonarScannerToolName = "SonarQube", Boolean abortIfQualityGateFails = true) {
    timestamps {
        ansiColor('xterm') {
            checkout scm
            sh 'git submodule update --init'
            script {
                timeout(time: 1, unit: 'HOURS') {
                    def sonarHome = tool sonarScannerToolName;
                    withSonarQubeEnv(sonarScannerToolName) {
                        sh "${sonarHome}/bin/sonar-scanner"
                    }
                    if (abortIfQualityGateFails) {
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
