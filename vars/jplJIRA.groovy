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
 
*/
def checkProjectExists(cfg) {
    if (cfg.jiraProjectKey == '') {
        cfg.jiraProject = ''
    }
    else {
        script {
            // Look at https://jenkinsci.github.io/jira-steps-plugin/jira_get_project.html for more info
            def jiraProject = jiraGetProject idOrKey: cfg.jiraProjectKey
            cfg.jiraProject = jiraProject
        }
    }
}
