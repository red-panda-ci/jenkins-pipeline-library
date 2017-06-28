/**

  Post build tasks

  Parameters:
  * cfg jplConfig class object

  cfg usage:
  - cfg.targetPlatform
  - cfg.notify
  - cfg.jiraProjectKey

*/
def call(cfg) {

    timestamps {
        ansiColor('xterm') {
            script {
                resultStatus = (currentBuild.result == null ? 'SUCCESS' : currentBuild.result)
                branchInfo = (env.BRANCH_NAME == null ? '' : " (branch ${env.BRANCH_NAME})")

                if (resultStatus == 'SUCCESS') {
                    // Notify on success
                    if (cfg.notify) {
                        jplNotify(cfg.recipients.hipchat,'','')
                    }
                }
                else {
                    jplJIRA.openIssue(cfg)
                    // Notify on failure
                    if (cfg.notify) {
                        jplNotify(cfg.recipients.hipchat,cfg.recipients.slack,cfg.recipients.email)
                    }
                }
            }
        }
    }
}
