/**

  Build APK with Fastlane within docker into Jenkins, based on jpl project configuration

  Parameters:
  * jplConfig project config class

*/
def call(jplConfig) {
    timestamps {
        ansiColor('xterm') {
            sh "ci-scripts/.jenkins_library/bin/buildApk.sh --sdkVersion=${jplConfig.projectName} --command='fastlane ${jplConfig.laneName} versionSuffix:${jplConfig.versionSuffix}'"
            script {
                archive '**/*DebugUnitTest.exec'
                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    archive '**/*.apk'
                }
            }
        }
    }
}
