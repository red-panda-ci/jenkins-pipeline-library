/**

  Launch SonarQube scanner

  Parameters:
  * cfg jplConfig class object

  cfg usage:
  * cfg.sonar.*

  To use the jplSonarScanner() tool:
  * Configure Jenkins with SonarQube >= 6.2
  * Configure a webhook in Sonar to your jenkins URL <your-jenkins-instance>/sonar-webhook/ (https://jenkins.io/doc/pipeline/steps/sonar/#waitforqualitygate-wait-for-sonarqube-analysis-to-be-completed-and-return-quality-gate-status)

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
                        if (!(qg.status in ['OK', 'WARN'])) {
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
    }
}
