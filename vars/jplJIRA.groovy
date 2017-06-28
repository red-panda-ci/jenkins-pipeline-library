/**

  JIRA management

  Parameters:
  * cfg jplConfig class object

*/
def call(cfg) {
}

/**

  Check if the project exists.
  Finish job with error if
  - The project doen't exist in JIRA, and
  - JIRA_FAIL_ON_ERROR env variable (or failOnError parameter) is set on "true"

  Parameters:
  * cfg jplConfig object

  cfg usage:
  - cfg.jira.*
 
*/
def checkProjectExists(cfg) {
    if (cfg.jira.projectKey != '') {
        script {
            // Look at https://jenkinsci.github.io/jira-steps-plugin/jira_get_project.html for more info
            def jiraProject = jiraGetProject idOrKey: cfg.jira.projectKey
            cfg.jira.projectData = jiraProject
        }
    }
}

/**

  Open jira issue

  Parameters:
  * cfg jplConfig object

  cfg usage:
  - cfg.jira.*
 
*/
def openIssue(cfg) {
    if (cfg.jira.projectKey != '') {
        // Open JIRA tickets on 'NOT SUCCESS build'
        echo "jpl: open jira issue in JIRA project with key: ${cfg.jira.projectKey}"
        def issueData = [fields: [
                                project: [key: cfg.jira.projectKey],
                                summary: "Job [${env.JOB_NAME}] [#${env.BUILD_NUMBER}] finished with ${resultStatus}${branchInfo}",
                                description: "View details on ${env.BUILD_URL}console",
                                issuetype: [id: '1']  // type 1: bug
                                ]]
        response = jiraNewIssue issue: issueData
    }
}