/**

  JIRA management

  Parameters:
  * cfg jplConfig class object

*/
def call(cfg) {
}

/**

  Check if the project exists.
  End job with error if
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
            println env
            // Look at https://jenkinsci.github.io/jira-steps-plugin/jira_get_project.html for more info
            def jiraProject = jiraGetProject idOrKey: cfg.jira.projectKey
            cfg.jira.projectData = jiraProject
        }
    }
}
