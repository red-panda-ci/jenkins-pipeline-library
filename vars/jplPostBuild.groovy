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
                if (currentBuild.result == null) {
                    resultStatus = 'SUCCESS'
                }
                else {
                    resultStatus = currentBuild.result
                }
                if (env.BRANCH_NAME == null) {
                    branchInfo = ""
                }
                else {
                    branchInfo = " (branch ${env.BRANCH_NAME})"
                }

                echo "jpl: process build result status: ${resultStatus}"
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
