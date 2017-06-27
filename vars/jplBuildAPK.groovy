/**

  Build APK with Fastlane within docker into Jenkins, based on jpl project configuration

  Parameters:
  * cfg jplConfig class object

  cfg usage:
  * cfg.projectName
  * cfg.laneName
  * cfg.versionSuffix

*/
def call(cfg,command='') {
    if (command == '') {
        command="fastlane ${cfg.laneName} versionSuffix:${cfg.versionSuffix}"
    }
    timestamps {
        ansiColor('xterm') {
            sh "ci-scripts/.jenkins_library/bin/buildApk.sh --sdkVersion=${cfg.projectName} --command='${command}'"
            script {
                archive '**/*DebugUnitTest.exec'
                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    archive '**/*.apk'
                }
            }
        }
    }
}
