/**

  Build APK with Fastlane within docker into Jenkins

  Parameters:
  * String laneName What is the lane of Fastlane that's going to be used in the build
  * String versionSuffix Version suffix. It's added to versionName in the APK

*/
def call(jplConfig) {
    timestamps {
        ansiColor('xterm') {
            sh "fastlane ${jplConfig.laneName} versionSuffix:${jplConfig.versionSuffix}"
        }
    }
}
