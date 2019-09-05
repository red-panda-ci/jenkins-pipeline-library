/**
Start library activities

This helper should be executed as first step of the pipeline.

* Prepare some things based on the target platform:
  * "android". Prepare the workspace to build within native Docker of the Jenkins
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
* jplValidateCommitMessages

cfg usage:

* cfg.targetPlatform
* cfg.flags.isJplStarted
*/
def call(cfg) {
    jplConfig.checkInitializationStatus(cfg)
    if (cfg.flags.isJplStarted) {
        error ("ERROR: jplStart already executed")
    }
    cfg.lastTag = sh (
        script: "git describe --abbrev=0 --tags||echo ''",
        returnStdout: true
    ).trim()
    if (cfg.commitValidation.enabled && cfg.BRANCH_NAME.startsWith('PR')) {
        jplValidateCommitMessages(cfg)
    }
    // Build, archive and attach HTML changelog report to the build
    if (cfg.changelog.enabled) {
        sh "mkdir -p ci-scripts/reports"
        jplBuildChangelog(cfg, 'html', 'ci-scripts/reports/CHANGELOG.html')
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
        fileOperations([fileDeleteOperation(includes: 'ci-scripts/reports/CHANGELOG.html')])
    }
    cfg.flags.isJplStarted = true
}
