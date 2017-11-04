/**

Start library activities

This helper should be executed as first step of the pipeline.

* Prepare some things based on the target platform:
  * "android". Prepare the workspace to build within native Docker of the Jenkins:
  * Get the contents of the repository https://github.com/red-panda-ci/ci-scripts on the ci-scripts/.jenkins_library repository
  * "ios" (TBD)
  * "hybrid" (TBD)
  * "backend" (TBD)
* Execute for the jplValidateCommitMessages on Pull Request, breaking the build if the messages don't complaint with the parse rules
* Execute jplBuildChangelog and attach the CHANGELOG.html as artifact of the build

Parameters:

* cfg jplConfig class object

jpl usage:

* jplBuildChangeLog
* jplCheckoutSCM
* jplIE
* jplValidateCommitMessages

cfg usage:

* cfg.targetPlatform
* cfg.isJplStarted

*/
def call(cfg) {
    script {
        if (cfg.isJplStarted) {
            error ("ERROR: jplStart already executed")
        }
        jplCheckoutSCM(cfg)
        cfg.lastTag = sh (
            script: "git describe --abbrev=0 --tags||echo ''",
            returnStdout: true
        ).trim()
        jplIE(cfg)
        sh 'git submodule update --init'
        if (cfg.targetPlatform == 'android') {
            sh "rm -rf ci-scripts/.jenkins_library && mkdir -p ci-scripts/.temp && cd ci-scripts/.temp/ && wget -q -O - https://github.com/red-panda-ci/ci-scripts/archive/master.zip | jar xvf /dev/stdin > /dev/null && chmod +x ci-scripts-master/bin/*.sh && mv ci-scripts-master ../.jenkins_library"
        }
        if (cfg.commitValidation.enabled && cfg.BRANCH_NAME.startsWith('PR')) {
            jplValidateCommitMessages(cfg)
        }
        // Build, archive and attach HTML changelog report to the build
        if (cfg.buildChangelog) {
            sh "mkdir -p ci-scripts/reports"
            jplBuildChangelog(cfg, 'HEAD', 'html', 'ci-scripts/reports/CHANGELOG.html')
            archiveArtifacts artifacts: 'ci-scripts/reports/CHANGELOG.html', fingerprint: true, allowEmptyArchive: false
            // publish html
            // snippet generator doesn't include "target:"
            // https://issues.jenkins-ci.org/browse/JENKINS-29711.
            publishHTML (target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: false,
                reportDir: 'ci-scripts/reports',
                reportFiles: 'CHANGELOG.html',
                reportName: "Changelog"
                ])
            cfg.changelogIsBuilded = true;
            fileOperations([fileDeleteOperation(excludes: '', includes: 'ci-scripts/reports/CHANGELOG.html')])
        }
        cfg.isJplStarted = true
    }
}
