/**

  Get shared libraries

*/
def call() {
    timestamps {
        ansiColor('xterm') {
            sh "mkdir -p ci-scripts/tmp && cd ci-scripts/tmp && wget -q https://github.com/pedroamador/ci-scripts/archive/develop.zip -O ci-scripts_develop.zip && unzip -o ci-scripts_develop.zip && rm ci-scripts_develop.zip"
        }
    }
}
