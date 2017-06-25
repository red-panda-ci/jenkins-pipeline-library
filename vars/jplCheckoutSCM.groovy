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
                // The commented code fragment bellow requires to aprove some "scm.*" functions in Jenkins
                /*
                checkout([
                    $class: 'GitSCM',
                    branches: scm.branches,
                    doGenerateSubmoduleConfigurations: false,
                    extensions: scm.extensions +
                        [[$class: 'SubmoduleOption',
                        disableSubmodules: false,
                        parentCredentials: true,
                        recursiveSubmodules: true,
                        reference: '',
                        trackingSubmodules: false]],
                    userRemoteConfigs: scm.userRemoteConfigs
                ])
                */
                checkout scm

                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    sh 'git checkout ' + env.BRANCH_NAME + ' && git pull '
                }
                sh 'git submodule update --init'
                if (jplConfig.targetPlatform == 'android') {
                    sh "rm -rf ci-scripts/.jenkins_library && mkdir -p ci-scripts/.temp && cd ci-scripts/.temp/ && wget -q -O - https://github.com/pedroamador/ci-scripts/archive/develop.zip | jar xvf /dev/stdin > /dev/null && chmod +x ci-scripts-develop/bin/*.sh && mv ci-scripts-develop ../.jenkins_library"
                    //sh "cp ci-scripts/.jenkins_library/docker/android-emulator/Dockerfile Dockerfile && cat Dockerfile.tail >> Dockerfile"
                }
            }
        }
    }
}
