/**

  Global config variables

  Parameters:
  * String projectName
  * String targetPlatform
  * String jiraProjectKey

  ---------------
  cfg definitions
  ---------------
  * string  cfg.projectName             Project alias / codename (with no spaces)
  * string  cfg.laneName                Fastlane lane name
  * string  cfg.targetPlatform          Target platform, one of these
    - "android"
    - "ios"
    - "hybrid"
  * String  cfg.jiraProjectKey          JIRA project key
  * String  cfg.sonarScannerToolName    The name of the SonarQube tool name configured in your Jenkins installation
  * Boolean cfg.abortIfQualityGateFails Abort the job with error result. You must have a webhook configured in SonarQube to your Jenkins
  * Hashmap cfg.recipients              Recipients for hipchat, slack and email
    - cfg.recipients.hipchat => List of hipchat rooms, comma separated
    - cfg.recipients.slack   => List of slack channels, comma separated
    - cfg.recipients.email   => List of email address, comma separated
  * object  cfg.jiraProject             Jira project data
    - cfg.jiraProject.data['name'] => Project name
  
*/
public String   projectName
public String   laneName
public String   versionSuffix
public String   targetPlatform
public String   jiraProjectKey
public String   sonarScannerToolName
public String   abortIfQualityGateFails
public HashMap  recipients
public jiraProject

def call (projectName,targetPlatform = '',jiraProjectKey = '') {
    // Set config values
    if (env.BRANCH_NAME == null) {
        this.branchName = 'develop'
    }
    else {
        this.branchName = env.BRANCH_NAME
    }
    this.projectName                = projectName
    this.laneName                   = ((branchName in ["staging","quality","master"]) || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
    this.versionSuffix              = (branchName == "master") ? '' :  "rc" + env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
    this.targetPlatform             = targetPlatform
    this.jiraProjectKey             = jiraProjectKey
    this.sonarScannerToolName       = "SonarQube"
    this.abortIfQualityGateFails    = true
    this.recipients                 = [hipchat:'',slack:'',email:'']
    // Do some checks
    jplJIRA.checkProjectExists(this)
    return this
}
