/**
Get the code from SCM and init
Leave the repository on the actual branch, instead of "deatached"

Parameters:

* cfg jplConfig class object

cfg uage:

* cfg.repository.url
* cfg.repository.branch
*/
def call(cfg) {
    script {
        if (cfg.repository.url == '') {
            checkout scm
            if (!cfg.BRANCH_NAME.startsWith('PR-')) {
                sh 'git checkout ' + cfg.BRANCH_NAME + ' && git pull'
            }
        }
        else {
            if (cfg.repository.branch == '') {
                git url: cfg.repository.url
            }
            else {
                git branch: cfg.repository.branch, url: cfg.repository.url
            }
        }
    }
}
