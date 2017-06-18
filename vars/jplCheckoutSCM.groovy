/**

  Checkout SCM

  Get the code from SCM and init / update submodules
  Leave the repository on the actual branch, instead of "deatached"

*/
def call() {
    timestamps {
        ansiColor('xterm') {
            sh 'git checkout ' + env.BRANCH_NAME + ' && git pull && git submodule update --init'
        }
    }
}
