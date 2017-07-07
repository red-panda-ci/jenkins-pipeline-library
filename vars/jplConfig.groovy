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
  * string  BRANCH_NAME             Branch name                                     (default: env.BRANCH_NAME)
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
        cfg.BRANCH_NAME = 'develop'
    }
    else {
        cfg.BRANCH_NAME = env.BRANCH_NAME
    }
    cfg.projectName                         = projectName
    cfg.laneName                            = ((cfg.BRANCH_NAME in ["staging", "qa", "quality", "master"]) || cfg.BRANCH_NAME.startsWith('release/')) ? cfg.BRANCH_NAME.tokenize("/")[0] : 'develop'
    cfg.versionSuffix                       = (cfg.BRANCH_NAME == "master") ? '' :  "rc" + env.BUILD_NUMBER + "-" + cfg.BRANCH_NAME.tokenize("/")[0]
    cfg.targetPlatform                      = targetPlatform
    switch (cfg.targetPlatform) {
        case 'android':
            cfg.archivePattern = '**/*.apk'
            break;
        case 'ios':
            cfg.archivePattern = '**/*.ipa'
            break;
        default:
            cfg.artifactsPattern = ''
            break;
    }

    //
    cfg.jira = [:]
        cfg.jira.projectKey                 = jiraProjectKey
        cfg.jira.projectData                = ''

    //
    cfg.sonar = [:]
        cfg.sonar.toolName                  = "SonarQube"
        cfg.sonar.abortIfQualityGateFails   = true
    
    //
    cfg.notify                              = true
    cfg.recipients                          = recipients

    //-----------------------------------------//

    // Do some checks
    jplJIRA.checkProjectExists(cfg)

    // Return config HashMap
    return cfg
}
