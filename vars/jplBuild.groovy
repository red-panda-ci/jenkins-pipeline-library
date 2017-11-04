/**

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

*/
def call(cfg, String command = '') {
    switch (cfg.targetPlatform) {
        case 'android':
            jplBuildAPK(cfg,command)
            break;
        case 'ios':
            echo "Todo: build IPA specific task"
            break;
        default:
            echo "Unknown target platform"
            break;
    }
}

/**
  Return result status of current build
  Return "SUCCESS" on unknown case
*/
def resultStatus() {
    return (currentBuild.result == null ? 'SUCCESS' : currentBuild.result)
}
/**
  Return branch info of current job (blank if none)
  Used in issues and notifications
 */
def branchInfo() {
    return (env.BRANCH_NAME == null ? '' : " (branch ${env.BRANCH_NAME})")
}
/**
  Summary field for issues and notifications
 */
def summary(summary = '') {
    return (summary == '' ? "Job [${env.JOB_NAME}] [#${env.BUILD_NUMBER}] finished with ${this.resultStatus()}${this.branchInfo()}" : summary)
}
/**
  Description text for issues and notifications
 */
def description(description = '') {
    return (description == '' ? "View details on ${env.BUILD_URL}console" : description)
}
