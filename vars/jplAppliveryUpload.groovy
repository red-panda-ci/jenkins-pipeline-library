/**

  Upload package to applivery

  Parameters:
  * cfg jplConfig class object
  * string package File name to upload

  cfg usage:
  * cfg.applivery[:] hashmap
  * cfg.versionSuffix
  
*/
def call(cfg,package) {
    timestamps {
        ansiColor('xterm') {
            script {
                os = artifact.toLowerCase().endsWith('ipa') ? 'ipa' : 'apk'
                sh """wget -O - https://raw.githubusercontent.com/pedroamador/jenkins-deploy-script/master/jenkins.sh | bash -s -- \
                        --apikey="${cfg.applivery.token}" \
                        --app="${cfg.applivery.app}" \
                        --os="${os}" \
                        --package="${package}" \
                        --versionName="${cfg.versionSuffix}" \
                        --notes="branch:${BRANCH_NAME}, build: #${BUILD_NUMBER}" \
                        --notify=${cfg.applivery.notify} \
                        --tags="${cfg.applivery.tags}" \
                        --autoremove=${cfg.applivery.autoremove} \
                        --deployer="jenkins"
                    """
            }
        }
    }
}
