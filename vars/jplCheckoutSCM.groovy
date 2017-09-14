/**

  Checkout SCM

  Get the code from SCM and init / update submodules
  Leave the repository on the actual branch, instead of "deatached"

  Parameters:
  * cfg jplConfig class object
  * String repository Repository URL (https://... git@...)
                      Leave it blank to use "checkout scm" command (multibranch project)
  * String branch Branch name (blank for HEAD)

  cfg usage:
  * cfg.targetPlatform

  This function will do some things for you based on the target platform:

  * "android". Prepare the workspace to build within native Docker of the Jenkins:
  * Get the contents of the repository https://github.com/pedroamador/ci-scripts on the ci-scripts/.jenkins_library repository
  * "ios" (TBD)
  * "hybrid" (TBD)
  * "backend" (TBD)

  Also, they execute for the jplValidateCommitMessages on Pull Request, breaking the build if the messages don't complaint with the parse rules

*/
def call(cfg, repository = '', branch = '') {
    timestamps {
        ansiColor('xterm') {
            script {
                if (repository == '') {
                    checkout scm
                    if (!cfg.BRANCH_NAME.startsWith('PR-')) {
                        sh 'git checkout ' + cfg.BRANCH_NAME + ' && git pull '
                    }
                }
                else {
                    if (branch == '') {
                        git url: repository
                    }
                    else {
                        git branch: branch, url: repository
                    }
                }
                cfg.lastTagBranch = sh (
                    script: "git describe --abbrev=0 --tags||echo ''",
                    returnStdout: true
                ).trim()
                jplIE(cfg)
                sh 'git submodule update --init'
                if (cfg.targetPlatform == 'android') {
                    sh "rm -rf ci-scripts/.jenkins_library && mkdir -p ci-scripts/.temp && cd ci-scripts/.temp/ && wget -q -O - https://github.com/pedroamador/ci-scripts/archive/master.zip | jar xvf /dev/stdin > /dev/null && chmod +x ci-scripts-master/bin/*.sh && mv ci-scripts-master ../.jenkins_library"
                }
                if (cfg.commitValidation.enabled && cfg.BRANCH_NAME.startsWith('PR')) {
                    jplValidateCommitMessages(cfg)
                }
                // Build, archive and attach HTML changelog report to the build
                if (cfg.changelogIsBuilded == null) {
                    repositoryUrl = sh (
                        script: "git ls-remote --get-url",
                        returnStdout: true
                    )
                    .trim()
                    .replace('git@github.com:','https://github.com/')
                    .replace('git@bitbucket.org:','https://bitbucket.org/')
                    sh "mkdir -p ci-scripts/reports && git log --format='%B%n-hash-%n%H%n-gitTags-%n%d%n-committerDate-%n%ci%n------------ _¿? ------------' HEAD --no-merges | docker run --rm -i -e COMMIT_DELIMITER='------------ _¿? ------------' -e PRESET='eslint' -e GIT_URL='${repositoryUrl}' -e FORMAT='html' madoos/node-pipe-changelog-generator > ci-scripts/reports/CHANGELOG.html"
                    archiveArtifacts artifacts: 'ci-scripts/reports/CHANGELOG.html', fingerprint: true, allowEmptyArchive: false

                    // publish html
                    // snippet generator doesn't include "target:"
                    // https://issues.jenkins-ci.org/browse/JENKINS-29711.
                    publishHTML (target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: false,
                        reportDir: 'ci-scripts/reports',
                        reportFiles: 'CHANGELOG.html',
                        reportName: "Changelog"
                        ])
                    cfg.changelogIsBuilded = true;
                }
            }
        }
    }
}
