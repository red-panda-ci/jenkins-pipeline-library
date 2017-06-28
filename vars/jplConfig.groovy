/**

  Global config variables

  Parameters:
  * String projectName
  * String targetPlatform
  * String jiraProjectKey
  * HashMap recipients

  ---------------
  cfg definitions
  ---------------
  * string  projectName             Project alias / codename (with no spaces)
  * string  laneName                Fastlane lane name
  * string  targetPlatform          Target platform, one of these
    - "android"
    - "ios"
    - "hybrid"
  * String  jiraProjectKey          JIRA project key
  
  * HashMap sonar                         Sonar scanner config
  * String sonar.toolName                 Tool name configured in Jenkins  (default: "SonarQube")
  * String sonar.abortIfQualityGateFails  Tool name configured in Jenkins  (default: "SonarQube")

  * String  sonarScannerToolName    The name of the SonarQube tool name configured in your Jenkins installation
  * boolean abortIfQualityGateFails Abort the job with error result. You must have a webhook configured in SonarQube to your Jenkins
  * boolean notify                  Automatically send notifications
  * Hashmap recipients              Recipients for hipchat, slack and email
    - recipients.hipchat => List of hipchat rooms, comma separated
    - recipients.slack   => List of slack channels, comma separated
    - recipients.email   => List of email address, comma separated
  * object  jiraProject             Jira project data
    - jiraProject.data['name'] => Project name
  
*/
def call (projectName,targetPlatform = '',jiraProjectKey = '',recipients = [hipchat:'',slack:'',email:'']) {
    cfg = [:]
    // 
    if (env.BRANCH_NAME == null) {
        branchName = 'develop'
    }
    else {
        branchName = env.BRANCH_NAME
    }
    cfg.projectName                = projectName
    cfg.laneName                   = ((branchName in ["staging","quality","master"]) || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
    cfg.versionSuffix              = (branchName == "master") ? '' :  "rc" + env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
    cfg.targetPlatform             = targetPlatform

    // 
    cfg.jira = [:]
        cfg.jira.projectKey = jiraProjectKey
        cfg.jira.projectData = ''

    // 
    cfg.sonar = [:]
        cfg.sonar.toolName = "SonarQube"
        cfg.sonar.abortIfQualityGateFails = true
    
    // Notifications
    cfg.recipients                 = recipients
    cfg.notify                     = true

    //-----------------------------------------//

    // Do some checks
    jplJIRA.checkProjectExists(cfg)
    // Return config HashMap
    return cfg
}
