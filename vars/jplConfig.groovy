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
/*
public String   projectName
public String   laneName
public String   versionSuffix
public String   targetPlatform
public String   jiraProjectKey
public String   sonarScannerToolName
public boolean  abortIfQualityGateFails
public HashMap  recipients
public boolean  notify
public jiraProject
*/
def call (projectName,targetPlatform = '',jiraProjectKey = '',recipients = [hipchat:'',slack:'',email:'']) {
    cfg = [:]
    // Set default config values
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
    cfg.jiraProjectKey             = jiraProjectKey
    cfg.sonarScannerToolName       = "SonarQube"
    cfg.abortIfQualityGateFails    = true
    cfg.recipients                 = recipients
    cfg.notify                     = true
    // Do some checks
    jplJIRA.checkProjectExists(cfg)
    // Return config HashMap
    return cfg
}
