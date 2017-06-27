/**

  Post build tasks

  Parameters:
  * cfg jplConfig class object

  cfg usage:
  * targetPlatform

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
                // Open JIRA tickets on 'NOT SUCCESS build'
                if ((resultStatus != 'SUCCESS') && (cfg.jiraProjectKey != '')) {
                    echo "jpl: open jira issue in JIRA project with key: ${cfg.jiraProjectKey}"
                    def issueData = [fields: [
                                              project: [key: cfg.jiraProjectKey],
                                              summary: "Job [${env.JOB_NAME}] [#${env.BUILD_NUMBER}] finished with ${resultStatus}${branchInfo}",
                                              description: "View details on ${env.BUILD_URL}console",
                                              issuetype: [id: '1']  // type 1: bug
                                              ]]
                    response = jiraNewIssue issue: issueData
                }
            }
        }
    }
}
