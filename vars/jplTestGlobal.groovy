/**

  Test gloval vars

*/
def call() {
    echo "Build number: ${env.BUILD_NUMBER}"
    echo "Global test var: ${globalTest}"
}
