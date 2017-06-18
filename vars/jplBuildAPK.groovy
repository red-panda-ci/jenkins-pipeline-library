/**

  Build APK with Fastlane
  Look at https://github.com/pedroamador/ci-scripts
  (require "ci-scripts" installation)

  Parameters:
  * String sdkVersion SDK Version to use
  * String lane What lane of Fastlane is going to be used in the build
  * String versionSuffix Version suffix

*/
def call(String sdkVersion, String lane,String versionSuffix = '') {
    timestamps {
        ansiColor('xterm') {
            jplCheckoutSCM()
            sh 'ci-scripts/common/bin/buildApk.sh --sdkVersion=' + sdkVersion + ' --command="fastlane ' + lane + ' versionSuffix:' + versionSuffix + '"'
        }
    }
}
