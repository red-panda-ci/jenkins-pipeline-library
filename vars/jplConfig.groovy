/**

  Global config variables

*/
public String projectName
public String laneName
public String versionSuffix
public String targetPlatform

def call (projectName) {
    if (env.BRANCH_NAME == null) {
        this.branchName = 'develop'
    }
    else {
        this.branchName = env.BRANCH_NAME
    }
    this.projectName = projectName
    this.laneName = ((branchName in ["staging","quality","master"]) || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
    this.versionSuffix = (branchName == "master") ? '' :  "rc" + env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
    this.targetPlatform = targetPlatform
}
