/**

  Build APK with Fastlane within docker into Jenkins, based on jpl project configuration

  Parameters:
  * cfg jplConfig class object
  * string command What is the command to be executed in the build
    Example: "./gradlew clean assembleDebug"

  cfg usage:
  * cfg.projectName
  * cfg.laneName
  * cfg.versionSuffix

  Notes:
  * Marked as DEPRECATED by jplBuild on 2017-09-02. Removed on a future release.

*/
def call(cfg,String command='') {
    timestamps {
        ansiColor('xterm') {
            // Build default
            if (cfg.ie.commandName == "fastlane") {
                for (int i = 0; i < cfg.ie.parameter.size(); i++) {
                    parameter = cfg.ie.parameter[i]
                    command = "fastlane ${parameter.name} versionSuffix:${cfg.versionSuffix}"
                    for (int j = 0; j < parameter.option.size(); j++) {
                        option = parameter.option[j]
                        command = "${command} ${option.name}:${option.status}"
                    }
                    print "Execute @ie on lane '${parameter.name}' with command '${command}'"
                    this.buildAPK(cfg,command)
                }
            }
            else {
                if (command == '') {
                    command = "fastlane ${cfg.laneName} versionSuffix:${cfg.versionSuffix}"
                }
                this.buildAPK(cfg,command)
            }
        }
    }
}

def buildAPK(cfg,command) {
    sh "ci-scripts/.jenkins_library/bin/buildApk.sh --sdkVersion=${cfg.projectName} --command='${command}'"
    archiveArtifacts artifacts: '**/*DebugUnitTest.exec', fingerprint: true, allowEmptyArchive: true
    if (!cfg.BRANCH_NAME.startsWith('PR-')) {
        archiveArtifacts artifacts: cfg.archivePattern, fingerprint: true, allowEmptyArchive: true
    }
}