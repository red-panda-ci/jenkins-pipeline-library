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
                checkout([
                    $class: 'GitSCM',
                    branches: scm.branches,
                    doGenerateSubmoduleConfigurations: true,
                    extensions: scm.extensions + [[$class: 'SubmoduleOption', parentCredentials: true]],
                    userRemoteConfigs: scm.usrRemoteConfigs
                ])

                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    sh 'git checkout ' + env.BRANCH_NAME + ' && git pull '
                }
                if (jplConfig.targetPlatform == 'android') {
                    sh "mkdir -p ?/.android && cp -n ~/.android/debug.keystore ?/.android && wget -O - https://raw.githubusercontent.com/pedroamador/ci-scripts/develop/docker/android-emulator/Dockerfile > Dockerfile && cat Dockerfile.tail >> Dockerfile"
                }
            }
        }
    }
}
