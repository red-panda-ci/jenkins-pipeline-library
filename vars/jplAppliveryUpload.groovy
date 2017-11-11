/**
Upload package to applivery

Parameters:

* cfg jplConfig class object
* String packageFile File name to upload. Should be an iOS / Android app artifact.
* String app App id
* String token Applivery account token

cfg usage:

* cfg.applivery[:] hashmap
* cfg.versionSuffix
*/
def call(cfg, String packageFile, String app = '', String token = '') {
    script {
        app = (app == '') ? cfg.applivery.app : app
        token = (token == '') ? cfg.applivery.token : token
        sh """wget -O - https://raw.githubusercontent.com/red-panda-ci/jenkins-deploy-script/master/jenkins.sh | bash -s -- \
                --apikey="${token}" \
                --app="${app}" \
                --os="${cfg.targetPlatform}" \
                --package="${packageFile}" \
                --versionName="${cfg.versionSuffix}" \
                --notes="branch:${BRANCH_NAME}, build: #${BUILD_NUMBER}" \
                --notify=${cfg.applivery.notify} \
                --tags="${cfg.applivery.tags}" \
                --autoremove=${cfg.applivery.autoremove} \
                --deployer="jenkins"
            """
    }
}
