# Jenkins Pipeline Library

[![Build Status](http://jenkins.redpandaci.com/buildStatus/icon?job=red-panda-ci/jenkins-pipeline-library/develop)](https://jenkins.redpandaci.com/job/red-panda-ci/job/jenkins-pipeline-library/job/develop/)

## Description

Library with a set of helpers to be used in Jenkins Scripted or Declarative Pipelines

This helpers are designed to be used in "Multibranch Pipeline" Jenkins job type, with "git flow" release cycle and at least with the following branches:

* develop
* master

## Usage

Add this line at the top of your Jenkinsfile

    @Library('github.com/red-panda-ci/jenkins-pipeline-library') _

Then you can use the helpers in your script

* Scripted Pipeline Example

TBD

* Declarative Pipeline example (Android)

```groovy
#!groovy

@Library('github.com/red-panda-ci/jenkins-pipeline-library') _

// Initialize cfg
cfg = jplConfig('project-alias', 'android', 'JIRAPROJECTKEY', [hipchat:'The-Project,Jenkins QA', slack:'#the-project,#integrations', email:'the-project@example.com,dev-team@example.com,qa-team@example.com'])

// The pipeline
pipeline {

    agent none

    stages {
        stage ('Initialize') {
            agent { label 'docker' }
            steps  {
                jplStart(cfg)
            }
        }
        stage ('Docker push') {
            agent { label 'docker' }
            steps  {
                jplDockerPush(cfg, 'the-project/docker-image', 'https://registry.hub.docker.com', 'dockerhub-credentials', 'dockerfile-path')
            }
        }
        stage ('Build') {
            agent { label 'docker' }
            steps  {
                jplBuild(cfg)
            }
        }
        stage('Test') {
            agent { label 'docker' }
            when { expression { (env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-') } }
            steps {
                jplSonarScanner(cfg)
            }
        }
        stage ('Sign') {
            agent { label 'docker' }
            when { branch 'release/v*' }
            steps  {
                jplSigning(cfg, "git@github.org:the-project/sign-repsotory.git", "the-project", "app/build/outputs/apk/the-project-unsigned.apk")
                archiveArtifacts artifacts: "**/*-signed.apk", fingerprint: true, allowEmptyArchive: false
            }
        }
        stage ('Release confirm') {
            when { branch 'release/v*' }
            steps {
                jplPromoteBuild(cfg)
            }
        }
        stage ('Release finish') {
            agent { label 'docker' }
            when { branch 'release/v*' }
            steps {
                jplCloseRelease(cfg)
            }
        }
        stage ('PR Clean') {
            agent { label 'docker' }
            when { branch 'PR-*' }
            steps {
                deleteDir()
            }
        }
    }

    post {
        always {
            jplPostBuild(cfg)
        }
    }

    options {
        timestamps()
        ansiColor('xterm')
        buildDiscarder(logRotator(artifactNumToKeepStr: '20',artifactDaysToKeepStr: '30'))
        disableConcurrentBuilds()
        skipDefaultCheckout()
        timeout(time: 1, unit: 'DAYS')
    }
}
```

## Helpers set

### jplAppetizeUpload

Upload package to appetize

Parameters:

* cfg jplConfig class object
* String packageFile File name to upload
* String app App ID
* String token Appetize token

cfg usage:

* cfg.appetize[:] hashmap

### jplAppliveryUpload

Upload package to applivery

Parameters:

* cfg jplConfig class object
* String packageFile File name to upload. Should be an iOS / Android app artifact.
* String app App id
* String token Applivery account token

cfg usage:

* cfg.applivery[:] hashmap
* cfg.versionSuffix

### jplBuild

Build iOS / Android app with Fastlane

- Android app will build using docker into Jenkins
- iOS app will build with fastlane directly

Both builds are based on jpl project configuration

Parameters:

* cfg jplConfig class object
* string command What is the command to be executed in the build

Example: "./gradlew clean assembleDebug"

cfg usage:

* cfg.targetPlatform

### jplBuildAPK

Build APK with Fastlane within docker into Jenkins, based on jpl project configuration

Parameters:

* cfg jplConfig class object
* string command What is the command to be executed in the build
Example: "./gradlew clean assembleDebug"

cfg usage:

* cfg.projectName
* cfg.laneName
* cfg.versionSuffix

Notes:

* Marked as DEPRECATED by jplBuild on 2017-09-02. Removed on a future release.

### jplBuildChangelog

  Build changelog file based on the commit messages

  You can build the changelog between two commits, tags or branches if you use range format "v1.1.0...v1.0.0"
  If you don fill the parameter then the "from HEAD to beginning" range is used

  Parameters:

  * cfg jplConfig class object
  * String range Commit range: tags, commits or branches    (defaults to "HEAD")
  * String format Changelog format: "md" or "html"          (defaults to "md")
  * String filename Changelog file name                     (defaults to "CHANGELOG.md")

  cfg usage:

  * cfg.BRNACH_NAME

### jplCheckoutSCM

Get the code from SCM and init
Leave the repository on the actual branch, instead of "deatached"

Parameters:

* cfg jplConfig class object

cfg uage:

* cfg.repository.url
* cfg.repository.branch

### jplCloseRelease

Close release (Branch "release/*")

Merge code from release/vX.Y.Z to "master" and "develop", then "push" to the repository.
Create new tag with "vX.Y.Z" to the commit

The function uses "git promote" script

Fails if your repository is not in a "release/*" branch

Parameters:
* cfg jplConfig class object

cfg usage:

* cfg.notify
* cfg.recipients

### jplConfig

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
  * String  targetPlatform          Target platform, one of these                   (default: "")
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

  * Hashmap ie: Integration Events configuration
        String ieCommitRawText      ie text as appears in commit message            (default: "" = no @ie command in the commit)
        String commandName          Command to be executed                          (default: "")
        Hashmap parameter           List of parameters and options                  (default: [:])
                                    Every parameter element of the hash contains:
                                    - String name: the string with the parameter
                                    - Hashmap option: List of options for the parameter.
                                    Every option of the hash contains:
                                    - String name: Name of the option
                                    - String status: "enabled" or "disabled", depending of the option status
  
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


### jplDockerBuild

Docker image build

Parameters:

* cfg jplConfig class object
* String dockerImageName Name of the docker image, defaults to cfg.projectName
* String dockerImageTag Tag of the docker image, defaults to "latest"
* String dockerfilePath The path where the Dockerfile is placed, default to the root path of the repository

cfg usage:

* cfg.projectName

### jplDockerPush

Docker image build & push to registry

Parameters:

* cfg jplConfig class object
* String dockerImageName Name of the docker image, defaults to cfg.projectName
* String dockerImageTag Tag of the docker image, defaults to "latest"
* String dockerfilePath The path where the Dockerfile is placed, default to the root path of the repository
* String dockerRegistryURL The URL of the docker registry. Defaults to https://registry.hub.docker.com
* String dockerRegistryJenkinsCredentials Jenkins credentials for the docker registry

cfg usage:

* cfg.projectName

### jplIE

Integration Events (IE) management

Parameters:

* cfg jplConfig class object

cfg usage:

* cfg.ie.*

Rules:

- The Integration Event line should start with '@ie'
- The event can have multiple parameters: "parameter1", "parameter2", etc.
- Every parameter can have multiple options, starting with "+" or "-": "+option1 -option2"
- If an option starts with "+" means the parameter "must have" the option
- If an option starts with "-" means the option "should not be" in the parameter
- You can't use an option after the command. It's mandatory to use a parameter

* Example:

    "@ie command parameter1 +option1 -option2 parameter2 +option1 +option2 -option3"

Commands:

- "fastlane": use multiple fastlane lanes, at least one. You can add multiple parameters in each

* Examples:

    "@ie fastlane develop"
    "@ie fastlane develop quality"
    "@ie fastlane develop -applivery +appetize quality +applivery -appetize"

- "gradlew": use gradle wrapper tasks

* Examples:

    "@ie gradlew clean assembleDebug"

### jplJIRA

JIRA management

Parameters:

* cfg jplConfig class object



/*
### jplNotify

Notify using multiple methods: hipchat, slack, email

Parameters:

* cfg jplConfig class object
* String summary The summary of the message (blank to use defaults)
* String message The message itself (blank to use defaults)

cfg usage:

* cfg.recipients.*

### jplPostBuild

Post build tasks

Parameters:

* cfg jplConfig class object

cfg usage:

* cfg.targetPlatform
* cfg.notify
* cfg.jiraProjectKey

Place the jplPostBuild(cfg) line into the "post" block of the pipeline like this

    post {
        always {
            jplPostBuild(cfg)
        }
    }

### jplPromoteBuild

Promote build to next steps, waiting for user input

Parameters:

* cfg jplConfig class object
* String message User input message, defaults to "Promote Build"
* String description User input description, defaults to "Check to promote the build, leave uncheck to finish the build without promote"

cfg usage:

* cfg.promoteBuild

### jplPromoteCode

Promote code on release

Merge code from upstream branch to downstream branch, then make "push" to the repository

The function uses "git promote" script of https://github.com/red-panda-ci/git-promote

Parameters:

* cfg jplConfig class object
* String updateBranch The branch "source" of the merge
* String downstreamBranch The branch "target" of the merge

### jplSigning

App signing management

Parameters:

* cfg jplConfig class object
* String signingRepository The repository (github, bitbucket, whatever) where the signing information lives
* String signingPath Relative path to locate the signing data within signing repository
* String artifactPath Path to the artifact file to be signed, relative form the build workspace

cfg usage:

* cfg.projectName

Notes:

* The artifactPath must be an unsigned APK, it's name should match the pattern "*-unsigned.apk"
* Your Jenkins instance must have read access to the repository containing signing data
* The signed artifact will be placed on the same route of the artifact to be signed, and named "*-signed.apk"
* The repository structure sould be like this:

    * Must have a "credentials.json" file with this content:

        {
            "STORE_PASSWORD": "store_password_value",
            "KEY_ALIAS": "key_alias_value",
            "KEY_PASSWORD": "key_password_value",
            "ARTIFACT_SHA1": "D7:22:FF:...."
        }

    * Must have a "keystore.jks", as the signing keystore file

    Both file should be placed in the a repository path, wich is informed with the "signingPath" parameter

### jplSonarScanner

Launch SonarQube scanner

Parameters:

* cfg jplConfig class object

cfg usage:

* cfg.sonar.*

To use the jplSonarScanner() tool:

* Configure Jenkins with SonarQube >= 6.2
* Configure a webhook in Sonar to your jenkins URL <your-jenkins-instance>/sonarqube-webhook/ (https://jenkins.io/doc/pipeline/steps/sonar/#waitforqualitygate-wait-for-sonarqube-analysis-to-be-completed-and-return-quality-gate-status)

### jplStart

Start library activities

This helper should be executed as first step of the pipeline.

* Prepare some things based on the target platform:
  * "android". Prepare the workspace to build within native Docker of the Jenkins
  * "ios" (TBD)
  * "hybrid" (TBD)
  * "backend" (TBD)
* Execute for the jplValidateCommitMessages on Pull Request, breaking the build if the messages don't complaint with the parse rules
* Execute jplBuildChangelog and attach the CHANGELOG.html as artifact of the build

Parameters:

* cfg jplConfig class object

jpl usage:

* jplBuildChangeLog
* jplCheckoutSCM
* jplIE
* jplValidateCommitMessages

cfg usage:

* cfg.targetPlatform
* cfg.flags.isJplStarted

### jplValidateCommitMessages

Validate commit messages on PR's using https://github.com/willsoto/validate-commit project

- Check a concrete quantity of commits on the actual PR on the code repository
- Breaks the build if any commit don'w follow the preset rules

Parameters:

* cfg jplConfig class object
* int quantity Number of commits to check
* String preset Preset to use in validation
  Should be one of the supported presets of the willsoto validate commit project:
  - angular
  - atom
  - eslint
  - ember
  - jquery
  - jshint

cfg usage:

* cfg.commitValidation.*

## Dependencies

You should consider the following configurations:

### Jenkins service

* Install Java "jre" and "jdk"
```console
$ apt-get install default-jre default-jdk
[...]
```
* Configure "git" to be able to make push to the repositories.
  * Configure git credentials (username, password) <https://git-scm.com/docs/git-credential-store> for use with "https" remote repositories and set "store" as global config option, with global user name and global email
    ```console
    $ git config --global credential.helper store
    $ git config --global user.name "Red Panda"
    $ git config --global user.email "redpandaci@gmail.com"
    $ cat .gitconfig
    [user]
        email = redpandaci@gmail.com
        name = Red Panda
    [push]
        default = simple
    [credential]
        helper = store
    $ cat ~/.git-credentials
    https://redpandaci%40gmail.com:fake-password@github.com
    ```
  * Configure ssh public key for use with "ssh" remote repositories.
  ```console
    $ ssh-keygen
    Generating public/private rsa key pair.
    Enter file in which to save the key (/var/lib/jenkins/.ssh/id_rsa): 
    Enter passphrase (empty for no passphrase):
    Enter same passphrase again:
    Your identification has been saved in /var/lib/jenkins/.ssh/id_rsa.
    Your public key has been saved in /var/lib/jenkins/.ssh/id_rsa.pub.
    The key fingerprint is:
    SHA256:+IKayZj7m06twLgUSWyb2jLINPjzp6uQeeyA8nmYjqk server@jenkins
    The key's randomart image is:
    +---[RSA 2048]----+
    |                 |
    |.                |
    | +               |
    |+ +    .         |
    |.B    . S        |
    |O*o. . .         |
    |#+Boo . .        |
    |o^oX. ..         |
    |E=^+++           |
    +----[SHA256]-----+
    jenkins@server:~$ cat .ssh/id_rsa.pub
    ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC72EdmruDtEoqF3BK7JPjgVGMfL7hnPVymdUEt76gk1U/sSaYsijbqxyhSbdp/8W7l1dwGA1Vs7cAn15qVzbUoJzmmM1rm7wPOBU7oBH1//oopA5U1XauXRuKWFQ8LDbjdaHBriBP4IyIG9fS+afgRwDlwlxx2mKuWhuYlHbBAxGwwDpxtTnvJ9JAnWG5eJ+8cXJ2PaIBlhc8jkjWkvLOnAWx729LdFQqWrikY5YwtNKw0CnU5XGBP96GcyR+k7PPkdr8LcVCewE042n6pw43e3H4GRlWU2w/nj/JniF6Tyx76hxSX9UMFiCKVXqM8blftqn9H7WGStt0b1pPhwtGT server@jenkins
  ```
* Install docker and enable Jenkins syste user to use the docker daemon.
* Install this plugins:
  * AnsiColor
  * Bitbucket Branch Source
  * Bitbucket Plugin
  * Blue Ocean
  * Copy Artifact Plugin
  * File Operations
  * Github Branch Source
  * Github Plugin
  * Git Plugin
  * HipChat, if you want to use hipchat as notification channel
  * HTML Publisher
  * JIRA Pipeline Steps, if you want to use a JIRA project
  * Pipeline
  * Pipeline Utility Steps, if you want to sign android APK's artifacts with jplSigning
  * Slack Notification, if you want to use Slack as notification channel
  * SonarQube Scanner, if you want to use SonerQube as quality gate with jplSonarScanner
  * Timestamper
* Setup Jeknins in "Configuration" main menu option
  * Enable the checkbox "Environment Variables" and add the following environment variables with each integration key:
    * APPETIZE_TOKEN
    * APPLIVERY_TOKEN
    * APPETIZE_TOKEN
    * JIRA_SITE
  * Put the correct Slack, Hipchat and JIRA credentials in their place (read the howto's of the related Jenkins plugins)
