/**

  Launch SonarQube scanner

  Parameters:
  * cfg jplConfig class object

  cfg usage:
  - cfg.sonar.*

*/
def call(cfg) {
    timestamps {
        ansiColor('xterm') {
            jplCheckoutSCM(cfg)
            script {
                timeout(time: 1, unit: 'HOURS') {
                    def sonarHome = tool cfg.sonar.toolName;
                    withSonarQubeEnv(cfg.sonar.toolName) {
                        sh "${sonarHome}/bin/sonar-scanner"
                    }
                    if (cfg.sonar.abortIfQualityGateFails) {
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
