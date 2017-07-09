/**

  Upload package to appetize

  Parameters:
  * cfg jplConfig class object
  * String packageFile File name to upload
  * String app App ID
  * String token Appetize token

  cfg usage:
  * cfg.appetize[:] hashmap
  * cfg.versionSuffix

*/
def call(cfg,String packageFile, String app = '', String token = '') {
    timestamps {
        ansiColor('xterm') {
            script {
                app = (app == '') ? cfg.applivery.app : app
                token = (token == '') ? cfg.applivery.token : token
                platform = packageFile.toLowerCase().endsWith('ipa') ? 'ios' : 'android'
                sh "curl https://${token}@api.appetize.io/v1/apps/${app} -X POST -F 'file=@${packageFile}' -F 'note=branch:${BRANCH_NAME}, build: #${BUILD_NUMBER}' -F 'platform=${platform}'"
            }
        }
    }
}
