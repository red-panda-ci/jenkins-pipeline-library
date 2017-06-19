/**

  Checkout SCM

  Get the code from SCM and init / update submodules
  Leave the repository on the actual branch, instead of "deatached"

  Parameters:
  * jplConfig project config class

*/
def call(jplConfig) {
    timestamps {
        ansiColor('xterm') {
            script {
                checkout scm
                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    sh 'git checkout ' + env.BRANCH_NAME + ' && git pull '
                }
                sh 'git submodule update --init'
                if (jplConfig.targetPlatform == 'android') {
                    sh "mkdir -p ?/.android && cp -n ~/.android/debug.keystore ?/.android && wget -O - https://raw.githubusercontent.com/pedroamador/ci-scripts/develop/docker/android-emulator/Dockerfile > Dockerfile && cat Dockerfile.tail >> Dockerfile"
                }
            }
        }
    }
}
