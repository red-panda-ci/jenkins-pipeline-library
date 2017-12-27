/**
Get the code from SCM and init
Leave the repository on the actual branch, instead of "deatached"

Parameters:

* cfg jplConfig class object

cfg uage:

* cfg.BRANCH_NAME
* cfg.repository.*
* cfg.repository.branch
*/
def call(cfg) {
    jplConfig.checkInitializationStatus(cfg)
    if (cfg.gitCache.enabled) {
        gitCacheProjectAbsolutePath = "${env.JENKINS_HOME}/${cfg.gitCache.gitCacheProjectRelativePath}"
        if (fileExists("${gitCacheProjectAbsolutePath}/.git/config")) {
            sh "echo 'jpl-git-cache: Hitting cache at ${gitCacheProjectAbsolutePath}' && rsync -a --delete ${gitCacheProjectAbsolutePath}/.git/ .git/"
        }
    }
    try {
        // First checkout try
        echo "jplCheckoutSCM: checkout code, first try"
        this.checkoutCode(cfg)
    } catch(err) {
        // Second checkout try after wipe project workspace
        echo "jplCheckoutSCM: wipe directory before second checkout try"
        deleteDir()
        this.checkoutCode(cfg)
    }
    if (cfg.gitCache.enabled) {
        sh "echo 'Storing cache at ${gitCacheProjectAbsolutePath}' && mkdir -p ${gitCacheProjectAbsolutePath} && rsync -a --delete .git/ ${gitCacheProjectAbsolutePath}/.git/"
    }
}

def checkoutCode(cfg) {
    if (cfg.repository.url == '') {
        checkout scm
        if (!cfg.BRANCH_NAME.startsWith('PR-')) {
            sh "git checkout ${cfg.BRANCH_NAME}; git pull"
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
