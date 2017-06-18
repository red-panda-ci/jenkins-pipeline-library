/**

  Checkout SCM

  Get the code from SCM and init / update submodules
  Leave the repository on the actual branch, instead of "deatached"

  Parameters:
  * String targetPlatform one of:
    - "android": copy debug.keystore from home folder ~/.android/debug.keystore
    - "": do nothing
*/
def call(String targetPlatform = '') {
    timestamps {
        ansiColor('xterm') {
            script {
                checkout scm
                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    sh 'git checkout ' + env.BRANCH_NAME + ' && git pull '
                }
                sh 'git submodule update --init'
                if (targetPlatform == 'android') {
                    sh "mkdir -p ?/.android && cp -n ~/.android/debug.keystore ?/.android"
                }
            }
        }
    }
}
