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
            echo "post build"
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

                // Open JIRA tickets on 'NOT SUCCESS build'
                echo "Result status: ${resultStatus}, cfg.jiraProjectKey: ${cfg.jiraProjectKey}"
                if ((resultStatus != 'SUCCESS') && (cfg.jiraProjectKey != '')) {
                    def issueData = [fields: [
                                              project: [key: cfg.jiraProjectKey,
                                              summary: "Job [${env.JOB_NAME}] [#${env.BUILD_NUMBER}] finished with ${resultStatus}${branchInfo}",
                                              description: "View details on ${env.BUILD_URL}/console",
                                              issuetype: [id: '1'], // type 1: bug,
                                              ]]
                    response = jiraNewIssue issue: issueData
                    echo "New JIRA issue opened: ${response.successful.toString()}"
                }
            }
        }
    }
}
