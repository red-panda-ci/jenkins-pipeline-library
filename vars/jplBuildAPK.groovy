/**

  Build APK with Fastlane
  Look at https://github.com/pedroamador/ci-scripts
  (require "ci-scripts" installation)

  Parameters:
  * String sdkVersion SDK Version to use
  * String lane What lane of Fastlane is going to be used in the build

*/
def call(String sdkVersion, String lane) {
    timestamps {
        ansiColor('xterm') {
            sh 'ci-scripts/common/bin/buildApk.sh --sdkVersion=' + sdkVersion + ' --lane="' + lane + '"'
        }
    }
}
