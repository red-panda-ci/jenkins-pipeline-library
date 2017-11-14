/**
Upload package to appetize

Parameters:

* cfg jplConfig class object
* String packageFile File name to upload
* String app App ID
* String token Appetize token

cfg usage:

* cfg.appetize[:] hashmap
*/
def call(cfg,String packageFile, String app = '', String token = '') {
    script {
        app = (app == '') ? cfg.appetize.app : app
        token = (token == '') ? cfg.appetize.token : token
        sh """curl https://${token}@api.appetize.io/v1/apps/${app} -X POST \
            -F "file=@${packageFile}" \
            -F "note=branch:${BRANCH_NAME}, build: #${BUILD_NUMBER}" \
            -F "platform=${cfg.targetPlatform}"
        """
    }
}
