/**

  Checkout SCM

  Get the code from SCM and init / update submodules
  Leave the repository on the actual branch, instead of "deatached"

*/
def call() {
    timestamps {
        ansiColor('xterm') {
            script {
                checkout scm
                if (!env.BRANCH_NAME.startsWith('PR-')) {
                    sh 'git checkout ' + env.BRANCH_NAME + ' && git pull '
                }
                sh 'git submodule update --init'
            }
        }
    }
}
