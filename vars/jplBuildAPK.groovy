/**

  Build APK with Fastlane within docker into Jenkins

  Parameters:
  * String laneName What is the lane of Fastlane that's going to be used in the build
  * String versionSuffix Version suffix

*/
def call(String laneName = '',String versionSuffix = '') {
    if (laneName == '') {
        buildLaneName = jplConfig.laneName
    }
    else {
        buildLaneName = laneName
    }
    if (versionSuffix == '') {
        buildVersionSuffix = jplConfig.versionSuffix
    }
    else {
        buildVersionSuffix = versionSuffix
    }
    timestamps {
        ansiColor('xterm') {
            sh "fastlane ${buildLaneName} versionSuffix:${buildVersionSuffix}"
        }
    }
}
