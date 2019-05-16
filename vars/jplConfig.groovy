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
  * String  projectName             Project alias / codename (with no spaces)       (default: "project")
  * String  BRANCH_NAME             Branch name                                     (default: env.BRANCH_NAME)
  * String  laneName                Fastlane lane name                              (default: related to branch name)
                                    The laneName is asigned to "[laneName]" part of the branch in case of "fastlane/[laneName]" branches
  * String  targetPlatform          Target platform, one of these                   (default: "any")
    - "android"
    - "ios"
    - "hybrid"
    - "backend"
  * boolean notify                  Automatically send notifications                (default: true)
  * String  archivePattern          Atifacts archive pattern
    Defaults
      - Android:  "** / *.apk"
      - iOS:      "** / *.ipa"
  * String releaseTag               Release tag for branches like "release/vX.Y.Z"  (default: related tag or "" on non-release branches)
                                    The releaseTag for this case is "vX.Y.Z"
  * String releaseTagNumber         Release tag for branches like "release/vX.Y.Z"  (default: related tag or "" on non-release branches)
                                    only the number part. Refers to "X.Y.Z" without the starting "v"
  * String androidPackages          SDK packages to install within docker image     (default: "build-tools-27.0.0,android-27")

  * Hashmap repository: repository parametes. You can use it for non-multibranch repository
        String url                  URL                                             (default: '')
        String branch               branch                                          (default: '')

  * Hashmap applivery: Applivery parameters
        String token                Account api key                                 (default: jenkins env.APPLIVERY_TOKEN)
        String app                  App ID                                          (default: jenkins env.APPLIVERY_APP)
        String tags                 Tags                                            (default: '')
        boolean notify              Send notifications                              (default: true)
        boolean autotemove          Auto remove old builds                          (default: true)

  * Hashmap appetize: Appetize parameters
        String token                Token                                           (default: jenkins env.APPETIZE_TOKEN)
        String app                  App                                             (default: jenkins env.APPETIZE_APP)
  
  * Hashmap recipients: Recipients used in notifications
        String recipients.hipchat   List of hipchat rooms, comma separated          (default: "")
        String recipients.slack     List of slack channels, comma separated         (default: "")
        String recipients.email     List of email address, comma separated          (default: "")

  * HashMap sonar: Sonar scanner configuration
        String sonar.toolName                 => Tool name configured in Jenkins    (default: "SonarQube")
        String sonar.abortIfQualityGateFails  => Tool name configured in Jenkins    (default: true)

  * HashMap jira: JIRA configuration
        String jira.projectKey      JIRA project key                                (default: "")
        object jira.projectData     JIRA project data                               (default: "")
  
  * Hashmap commitValidation: Commit message validation configuration on PR's, using project https://github.com/willsoto/validate-commit
        boolean enabled             Commit validation enabled status                (default: true)
        String preset               One of the willsoto validate commit presets     (default: 'eslint')
        int quantity                Number of commits to be checked                 (default: 1)

  * Hashmap changelog: Changelog building configuration
        boolean enabled             Automatically build changelog file              (default: true)
                                    * Archive as artifact build on every commit
                                    * Build and commit on jplCloseRelease
        String firstTag             First tag, branch or commit to be reviewed      (default: "")

  * Hashmap gitCache: Git cache configuration
        boolean enabled             Git cache status                                (default: true)
        String path                 Path to git cache files                         (default: ".jpl_temp/jpl-git-cache/")

  Other options for internal use:
  * Hashmap promoteBuild: Promote build workflow configuration
        Integer timeoutHours        * Number of hours to wait from user input       (default: 48)
        boolean enabled             * Flag to promote build to release steps        (default: false)

*/
def call (projectName = 'project', targetPlatform = 'any', jiraProjectKey = '', recipients = [hipchat:'',slack:'',email:'']) {
    cfg = [:]
    //
    if (env.BRANCH_NAME == null) {
        cfg.BRANCH_NAME = 'develop'
    }
    else {
        cfg.BRANCH_NAME = env.BRANCH_NAME
    }
    cfg.projectName                                 = projectName
    if (cfg.BRANCH_NAME.startsWith('fastlane/')) {
        cfg.laneName                                = cfg.BRANCH_NAME.tokenize("/")[1]
    }
    else {
        cfg.laneName                                = ((cfg.BRANCH_NAME in ["staging", "quality", "master"]) || cfg.BRANCH_NAME.startsWith('release/v') || cfg.BRANCH_NAME.startsWith('hotfix/v')) ? cfg.BRANCH_NAME.tokenize("/")[0] : 'develop'
    }
    cfg.targetPlatform                              = targetPlatform
    switch (cfg.targetPlatform) {
        case 'android':
            cfg.archivePattern = '**/*.apk'
            cfg.android = [:]
            break;
        case 'ios':
            cfg.archivePattern = '**/*.ipa'
            break;
        default:
            cfg.artifactsPattern = ''
            break;
    }
    cfg.androidPackages                             = 'build-tools-27.0.0,android-27'
    cfg.releaseTag                                  = (cfg.BRANCH_NAME.startsWith('release/v') || cfg.BRANCH_NAME.startsWith('hotfix/v')) ? cfg.BRANCH_NAME.tokenize("/")[1] : ""
    cfg.releaseTagNumber                            = (cfg.BRANCH_NAME.startsWith('release/v') || cfg.BRANCH_NAME.startsWith('hotfix/v')) ? cfg.BRANCH_NAME.tokenize("/")[1].substring(1) : ""

    //
    cfg.repository = [:]
    cfg.repository.url = ''
    cfg.repository.branch = ''

    //
    cfg.applivery = [:]
        cfg.applivery.token                         = (env.APPLIVERY_TOKEN == null) ? '' : env.APPLIVERY_TOKEN
        cfg.applivery.app                           = (env.APPLIVERY_APP   == null) ? '' : env.APPLIVERY_APP
        cfg.applivery.tags                          = ''
        cfg.applivery.notify                        = true
        cfg.applivery.autoremove                    = true

    //
    cfg.appetize = [:]
        cfg.appetize.token                          = (env.APPETIZE_TOKEN == null) ? '' : env.APPETIZE_TOKEN
        cfg.appetize.app                            = (env.APPETIZE_APP   == null) ? '' : env.APPETIZE_APP
    
    //
    cfg.notify                                      = true
    cfg.recipients                                  = recipients
    cfg.recipients.slack = cfg.recipients.slack ?: ''
    cfg.recipients.hipchat = cfg.recipients.hipchat ?: ''
    cfg.recipients.email = cfg.recipients.email ?: ''

    //
    cfg.sonar = [:]
        cfg.sonar.toolName                          = "SonarQube"
        cfg.sonar.abortIfQualityGateFails           = true

    //
    cfg.jira = [:]
        cfg.jira.projectKey                         = jiraProjectKey
        cfg.jira.projectData                        = ''

    //
    cfg.commitValidation                            = [:]
        cfg.commitValidation.enabled                = true
        cfg.commitValidation.preset                 = "eslint"
        cfg.commitValidation.quantity               = 1

    //
    cfg.changelog                                   = [:]
        cfg.changelog.enabled                       = true
        cfg.changelog.firstTag                      = ""

    //
    cfg.gitCache                                    = [:]
        cfg.gitCache.enabled                        = false
        cfg.gitCache.path                           = ".jpl_temp/git-cache"
        cfg.gitCache.gitCacheProjectRelativePath    = "${cfg.gitCache.path}/${cfg.projectName}-${cfg.targetPlatform}"

    //-----------------------------------------//

    //
    cfg.dockerFunctionPrefix                        = "docker run -i --rm -v jpl-cache:/var/lib/docker"
    cfg.promoteBuild                                = [:]
        cfg.promoteBuild.enabled                    = false
        cfg.promoteBuild.timeoutHours               = 48

    //
    cfg.flags = [:]
        cfg.flags.isJplConfigured                   = true
        cfg.flags.isJplStarted                      = false
        cfg.flags.isAndroidImageBuilded             = false
        cfg.flags.wereScriptsDownloaded             = false

    //-----------------------------------------//

    // Do some checks
    jplJIRA.checkProjectExists(cfg)

    // Return config HashMap
    return cfg
}

// Get jpl-scripts
def downloadScripts(cfg) {
    if (cfg.flags.wereScriptsDownloaded) {
        if (!fileExists('ci-scripts/.jpl-scripts/README.md')) {
            unstash 'jpl-scripts'
        }
    }
    else {
        sh "rm -rf ci-scripts/.jpl-scripts && mkdir -p ci-scripts/.temp && cd ci-scripts/.temp/ && wget -q -O - https://github.com/red-panda-ci/jpl-scripts/archive/master.zip | jar xvf /dev/stdin > /dev/null && chmod +x jpl-scripts-master/bin/*.sh && mv jpl-scripts-master ../.jpl-scripts"
        stash includes: 'ci-scripts/.jpl-scripts/**/*', name: 'jpl-scripts'
        cfg.flags.wereScriptsDownloaded = true
    }
}

def checkInitializationStatus(cfg) {
    if (!cfg.flags.isJplConfigured) {
        error ("ERROR: You should call to jplConfig first")
    }
}