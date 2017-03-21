package com.jenkins.pipeline.library

class Jpl implements Serializable {
    def gitPromoteCmd = "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- "
    def steps

    Jpl (steps) {
        this.steps = steps
    }

    def sonarScanner() {
        steps.timeout(time: 1, unit: 'HOURS') {
            steps.withSonarQubeEnv('SonarQube') {
                steps.sh "${sonarHome}/bin/sonar-scanner"
            }
            def qg = steps.waitForQualityGate()
            if (qg.status != 'OK') {
                steps.error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
    }
    def gitPromote(upstream,downstream) {
        steps.sh "echo ${gitPromoteCmd}"
        steps.sh "echo ${gitPromoteCmd} -m 'Merge from ${upstream} with Jenkins' ${downstream} quality"
    }
}
