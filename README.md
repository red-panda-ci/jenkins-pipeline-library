# Jenkins Pipeline Library

## Description

Library with a set of helpers to be used in Jenkins Scripted or Declarative Pipelines

## Notes

This helpers are designed to be used in "Multibranch Pipeline" Jenkins job type, with "git flow" release cycle and at least with the following branches:

* develop
* master

To use the jplSonarScanner() tool

* Configure Jenkins with SonarQube >= 6.2
* Configure a webhook in Sonar to your jenkins URL <your-jenkins-instance>/sonar-webhook/ (https://jenkins.io/doc/pipeline/steps/sonar/#waitforqualitygate-wait-for-sonarqube-analysis-to-be-completed-and-return-quality-gate-status)

## Use

Add this line at the top of your Jenkinsfile

    @Library('github.com/pedroamador/jenkins-pipeline-library') _

Then you can use the helpers in your script

* Scripted Pipeline Example

TBD

* Declarative Pipeline example (Android)

```groovy
#!groovy

@Library('github.com/pedroamador/jenkins-pipeline-library@develop') _

// Initialize cfg
cfg = jplConfig('project-alias', 'android', 'JIRAPROJECTKEY', [hipchat:'The-Project,Jenkins QA', slack:'#the-project,#integrations', email:'the-project@example.com,dev-team@example.com,qa-team@example.com'])

// The pipeline
pipeline {
    agent { label 'docker' }

    stages {
        stage ('Build') {
            steps  {
                jplCheckoutSCM(cfg)
                jplBuildAPK(cfg)
            }
        }
        stage('Sonarqube Analysis') {
            when { expression { (env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-') } }
            steps {
                jplSonarScanner(cfg)
            }
        }
        stage ('Confirm release') {
            agent none
            when { branch 'release/*' }
            steps {
                timeout(time: 1, unit: 'DAYS') {
                    input(message: 'Waiting for approval. Close release?')
                }
            }
        }
        stage ('Close release') {
            when { branch 'release/*' }
            steps {
                jplCloseRelease(cfg)
            }
        }
        stage ('Clean') {
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
        buildDiscarder(logRotator(artifactNumToKeepStr: '5'))
        disableConcurrentBuilds()
        skipDefaultCheckout()
        timeout(time: 2, unit: 'DAYS')
    }
}
```

## Helpers set

### jplConfig

Global static var with the pipeline configuration related to "jpl" library

If you want to use jplCheckoutSCM or jplBuildAPK helpers then you should initialize with

* jplConfig.initialize(env,"targetPlatform")

or init the values individually:

    [...]

    jplConfig.laneName = "laneName"
    jplConfig.versionSuffix = "versionSuffix"
    jplConfig.targetPlatform = "targetPlatform"

    [...]

Parameters of jplConfig.initialize:

* env (Object). The "env" object of the Jenkins build.
* targetPlatform (String). What is your target platform.

The initialize function will set the following configuration values:

* jplconfig.laneName (String). Lane name to be used with Fastlane. The value is related to the actual branch of the repository used in the build as following:
  * "staging", "quality", "master", "release/*" branches are going to set "staging", "quality", "master" and "release" lane name respectively.
  * The rest of branch names are going to set set "develop" lane name
* jplConfig.versionSuffix (String). String to be added to VersionName in Android APK builds. Default value is set to _build number_-_branch name_ if branch is diferent to "master" branch. For other than "staging", "quality" and "release/*" branch names will use "develop" instead.
  Example: if your are on the first build number of the "develop" branch and the versionName is set to "1.0.0" in build.gradle, the versionSuffix is set to "rc1-staging" and the versionName of the apk is finally set to "1.0.0-rc1-staging".
* targetPlatform (String). Default set to blank (""), should be one of:
  * "" (blank value). Not set.
  * "android". Set Android as target platform.

### jplCheckoutSCM

Parameters:

* jplConfig (Object). The jplConfig object already initialized (jplConfig.initialize).

Get the code from SCM repository (DSL command "checkout scm"). Aditionally:

* Change (checkout) to the actual branch name, if not named "PR-*" (pull request).
* Initialize submodules (shell "git submodule update --init")

This function will do some things for you based on the target platform:

* "android". Prepare the workspace to build within native Docker of the Jenkins:
  * Get the Dockerfile of https://github.com/pedroamador/ci-scripts/blob/develop/docker/android-emulator/Dockerfile saving it as "Dockerfile" of the workspace
  * Concatenate downloaded "Dockerfile" with the "Dockerfile.tail" of the repository.
  * Create "?/.android" folder on root of the workspace of the build and copy "~/.android/debug.keystore" into it, preparing to build the APK using pipeline native Docker.

### jplBuildAPK

Builds the Android APK based on jplConfig values "laneName" and "versionSuffix" using Fastlane. It will archive apk's as artifact builds if you are not in a pull request (branch "PR-*")

Parameters:

* jplConfig (Object). The jplConfig object already initialized (jplConfig.initialize).

### jplSonarScanner

Launch SonarQube scanner

Parameters:

* String sonarScanerToolName The name of the SonarQube tool name configured in your Jenkins installation. Default set to "SonarQube"
* Boolean abortIfQualityGateFails Abort the job with error result. You must have a webhook configured in SonarQube to your Jenkins. Default set to true

### jplPromote (deprecated)

Promote code on release

Merge code from upstream branch to downstream branch, then make "push" to the repository
When the code is safe in the repository, launch the job with the same name of the downstream branch and wait for the build result

The function uses "git promote" script from https://github.com/pedroamador/git-promote

Parameters:

* String updateBranch The branch "source" of the merge
* String downstreamBranch The branch "target" of the merge

Mantain for backwards compatibility and will be removed in the future.

### jplNotify

Notify using multiple methods (hipchat, slack, email) multiple channels or recipients each

Parameters:

* String hipchatRooms Comma-separated list of hipchat rooms to notify to (if empty, don't notify on hipchat)
* String slackChannels Comma-separated list of slack channels to notify to (if empty, don't notify on slack)
* String emailRecipients Comma-separated list of email recipients to notify to (if empty, don't send any mail)

### jplCloseRelease

Close release (Branch "release/*")

* Merge code from release/vX.Y.Z to "master" and "develop", then "push" to the repository.
* Create new tag with "vX.Y.Z" pointing to the commit, then "push" to the repository.
* Remove "release/vX.Y.Z" from the repository.

The function uses "git promote" script

Fails if your repository is not in a "release/*" branch
