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
    if (this.jiraProject == '') {
        cfg.jiraProject = ''
    }
    else {
        script {
            // Look at IssueInput class for more information.
            def jirProject = jiraGetProject idOrKey: cfg.jiraProjectKey
            echo 'JIRA project: ' + jiraProject.data['name'].toString()
            cfg.jiraProject = jiraProject
        }
    }
}