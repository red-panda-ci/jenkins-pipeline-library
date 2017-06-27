/**

  JIRA management

  Parameters:
  * cfg jplConfig class object

*/
def call(cfg) {
}

/**

  Check if the project exists

  Parameters:
  * cfg jplConfig object
 
*/
def checkProjectExists(cfg) {
    if (cfg.jiraProjectKey == '') {
        cfg.jiraProject = ''
    }
    else {
        script {
            // Look at IssueInput class for more information.
            def jiraProject = jiraGetProject idOrKey: cfg.jiraProjectKey
            cfg.jiraProject = jiraProject
        }
    }
}