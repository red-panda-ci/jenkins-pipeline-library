/**
Post build tasks

Parameters:

* cfg jplConfig class object

cfg usage:

* cfg.targetPlatform
* cfg.notify
* cfg.jiraProjectKey

Place the jplPostBuild(cfg) line into the "post" block of the pipeline like this

    post {
        always {
            jplPostBuild(cfg)
        }
    }
*/
def call(cfg) {
    jplConfig.checkInitializationStatus(cfg)
    if (cfg.notify) {
        jplNotify(cfg)
    }
}
