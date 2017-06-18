/**

  Build APK with Fastlane within docker into Jenkins

  Parameters:
  * String lane What lane of Fastlane is going to be used in the build
  * String versionSuffix Version suffix

*/
def call(String lane,String versionSuffix = '') {
    timestamps {
        ansiColor('xterm') {
            sh "fastlane ${lane} versionSuffi:${versionSuffix}"
        }
    }
}
