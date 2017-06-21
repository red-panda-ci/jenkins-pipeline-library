/**

  Build APK with Fastlane within docker into Jenkins, based on jpl project configuration

  Parameters:
  * jplConfig project config class

*/
def call(jplConfig) {
    timestamps {
        ansiColor('xterm') {
            sh "fastlane ${jplConfig.laneName} versionSuffix:${jplConfig.versionSuffix}"
            script {
                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    archive '**/*.apk'
                    archive '**/*DebugUnitTest.exec'
                }
            }
        }
    }
}
