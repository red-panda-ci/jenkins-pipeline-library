# Jenkins Pipeline Library

## Description

Library of helpers to be used in Jenkins Scripted or Declarative Pipelines

Use as a library with the "ci-scripts" project https://github.com/pedroamador/ci-scripts

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

* Declarative Pipeline example

```
#!groovy

@Library('github.com/pedroamador/jenkins-pipeline-library') _

pipeline {
    agent any
    stages {
        stage('Sonarqube Analysis') {
            agent any
            when { expression { ((env.BRANCH_NAME == 'develop') || env.BRANCH_NAME.startsWith('PR-')) ? true : false } }
            steps {
                // Launch SonarQube Scanner and wait for QualityGate result
                jplSonarScanner ("SonarQube",true)
            }
        }
        stage ('Build') {
            agent any
            when { expression { ((env.BRANCH_NAME in ['develop','quality','master']) || env.BRANCH_NAME.startsWith('PR-')) ? true : false } }
            steps  {
                echo "Mock: do the build on develop / quality / master branch or in a Pull Request (PR-*)"
            }
        }
        stage ('Promote to quality') {
            agent any
            when { branch 'release/*' }
            steps {
                // Pomote code from "release/*" to "quality" branch and launch the "quality" Jenkins job
                jplPromote (env.BRANCH_NAME,"quality")
            }
        }
        stage ('Confirm UAT') {
            agent none
            when { branch 'release/*' }
            steps {
                timeout(time: 5, unit: 'DAYS') {
                    input(message: 'Waiting for UAT. Release?')
                }
            }
        }
        stage ('Promote to master') {
            agent any
            when { branch 'release/*' }
            steps {
                // Pomote code from "quality" to "master" branch and launch the "master" Jenkins job
                jplPromote ("quality","master")
            }
        }
        stage ('Release') {
            agent any
            when { branch 'master' }
            steps  {
                echo "Mock: do the release only on master branch"
            }
        }
        stage ('Close release') {
            agent any
            when { branch 'release/*' }
            steps {
                // Close the release
                jplCloseRelease()
            }
        }
    }
    post {
        always {
            echo 'Finish'
        }
        success {
            echo 'good build'
            // Notify only on Hipchat
            jplNotify ('The-Project')
        }
        failure {
            echo 'bad'
            // Notify using all methods: hipchat, slack, email
            jplNotify ('The-Project','#the-project','the-project@example.com')
        }
    }
}
```

## Helpers set

### jplSonarScanner

Launch SonarQube scanner

Parameters:
* String sonarScanerToolName The name of the SonarQube tool name configured in your Jenkins installation
* Boolean abortIfQualityGateFails Abort the job with error result. You must have a webhook configured in SonarQube to your Jenkins

### jplPromote

Promote code on release

Merge code from upstream branch to downstream branch, then make "push" to the repository
When the code is safe in the repository, launch the job with the same name of the downstream branch and wait for the build result

The function uses "git promote" script

Parameters:
* String updateBranch The branch "source" of the merge
* String downstreamBranch The branch "target" of the merge


### jplNotify

Notify using multiple methods: hipchat, slack, email

Parameters:
* String hipchatRooms Specify what hipchat rooms to notify to (if empty, don't notify on hipchat)
* String slackChannels Specify what slack channels to notify to (if empty, don't notify on slack)
* String emailRecipients Specify what email recipients to notify to (if empty, don't send any mail)

### jplCloseRelease

Close release (Branch "release/*")

Merge code from release/vX.Y.Z to "master" and "develop", then "push" to the repository.
Create new tag with "vX.Y.Z" to the commit

The function uses "git promote" script

Fails if your repository is not in a "release/*" branch
