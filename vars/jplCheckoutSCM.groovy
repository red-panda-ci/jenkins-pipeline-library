/**

  Checkout SCM

  Get the code from SCM and init / update submodules
  Leave the repository on the actual branch, instead of "deatached"

  Parameters:
  * cfg jplConfig class object
  * String repository Repository URL (https://... git@...)
                      Leave it blank to use "checkout scm" command (multibranch project)
  * String branch Branch name (blank for HEAD)

  cfg usage:
  * targetPlatform

*/
def call(cfg,repository='',branch='') {
    timestamps {
        ansiColor('xterm') {
            script {
                if (repository == '') {
                    checkout scm
                    if (!cfg.BRANCH_NAME.startsWith('PR-')) {
                        sh 'git checkout ' + cfg.BRANCH_NAME + ' && git pull '
                    }
                }
                else {
                    if (branch == '') {
                        git url: repository
                    }
                    else {
                        git branch: branch, url: repository
                    }
                }
                cfg.lastTagBranch = sh (
                    script: "git describe --abbrev=0 --tags||echo ''",
                    returnStdout: true
                ).trim()
                sh 'git submodule update --init'
                if (cfg.targetPlatform == 'android') {
                    sh "rm -rf ci-scripts/.jenkins_library && mkdir -p ci-scripts/.temp && cd ci-scripts/.temp/ && wget -q -O - https://github.com/pedroamador/ci-scripts/archive/master.zip | jar xvf /dev/stdin > /dev/null && chmod +x ci-scripts-master/bin/*.sh && mv ci-scripts-master ../.jenkins_library"
                }
            }
        }
    }
}
