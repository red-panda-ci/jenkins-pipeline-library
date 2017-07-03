/**

  Checkout SCM

  Get the code from SCM and init / update submodules
  Leave the repository on the actual branch, instead of "deatached"

  Parameters:
  * cfg jplConfig class object
  * repository

  cfg usage:
  * targetPlatform

*/
def call(cfg,repository='',branch='') {
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
                if (repository == '') {
                    checkout scm
                    if (!cfg.BRANCH_NAME.startsWith('PR-')) {
                        sh 'git checkout ' + cfg.BRANCH_NAME + ' && git pull '
                    }
                }
                else {
                    deleteDir()
                    if (branch == '') {
                        branchInfo = ''
                    else {
                        branchInfo = " -b  + ${branch}"
                    }
                    sh "git clone ${repository} ${branchInfo}"
                }

                sh 'git submodule update --init'
                if (cfg.targetPlatform == 'android') {
                    sh "rm -rf ci-scripts/.jenkins_library && mkdir -p ci-scripts/.temp && cd ci-scripts/.temp/ && wget -q -O - https://github.com/pedroamador/ci-scripts/archive/master.zip | jar xvf /dev/stdin > /dev/null && chmod +x ci-scripts-master/bin/*.sh && mv ci-scripts-master ../.jenkins_library"
                }
            }
        }
    }
}
