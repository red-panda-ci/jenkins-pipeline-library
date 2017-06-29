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
  * string  projectName             Project alias / codename (with no spaces)       (default: "project")
  * string  laneName                Fastlane lane name                              (default: related to branch name)
  * string  targetPlatform          Target platform, one of these                   (default: "")
    - "android"
    - "ios"
    - "hybrid"
  * boolean notify                  Automatically send notifications                (default: true)
  * string  archivePattern          Atifacts archive pattern
    Defaults                                                                                                            (comment trick) */
//    - Android:  "**/*.apk"
//    - iOS:      "**/*.ipa"
                                                                                                                     /* (comment trick)
  * Hashmap recipients: Recipients used in notifications
        String recipients.hipchat => List of hipchat rooms, comma separated         (default: "")
        String recipients.slack   => List of slack channels, comma separated        (default: "")
        String recipients.email   => List of email address, comma separated         (default: "")

  * HashMap sonar: Sonar scanner configuration
        String sonar.toolName                 => Tool name configured in Jenkins    (default: "SonarQube")
        String sonar.abortIfQualityGateFails  => Tool name configured in Jenkins    (default: true)

  * HashMap jira: JIRA configuration
        String jira.projectKey  => JIRA project key                                 (default: "")
        object jira.projectData => Jira project data                                (default: "")
  
*/
def call (projectName = 'project', targetPlatform = '', jiraProjectKey = '', recipients = [hipchat:'',slack:'',email:'']) {
    cfg = [:]
    //
    if (env.BRANCH_NAME == null) {
        branchName = 'develop'
    }
    else {
        branchName = env.BRANCH_NAME
    }
    this.projectName                         = projectName
    this.laneName                            = ((branchName in ["staging","quality","master"]) || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
    this.versionSuffix                       = (branchName == "master") ? '' :  "rc" + env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
    this.targetPlatform                      = targetPlatform
    switch (this.targetPlatform) {
        case 'android':
            this.archivePattern = '**/*.apk'
            break;
        case 'ios':
            this.archivePattern = '**/*.ipa'
            break;
        default:
            this.artifactsPattern = ''
            break;
    }

    //
    this.jira = [:]
        this.jira.projectKey                 = jiraProjectKey
        this.jira.projectData                = ''

    //
    this.sonar = [:]
        this.sonar.toolName                  = "SonarQube"
        this.sonar.abortIfQualityGateFails   = true
    
    //
    this.notify                              = true
    this.recipients                          = recipients

    //-----------------------------------------//

    // Do some checks
    jplJIRA.checkProjectExists(this)

    // Return config HashMap
    return this
}
