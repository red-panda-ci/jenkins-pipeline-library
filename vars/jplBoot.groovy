/**

  Initialize jpl

*/
def call(String laneName = '',String versionSuffix = '') {
    if (env.BRANCH_NAME == null) {
        branchName = 'develop'
    }
    else {
        branchName = env.BRANCH_NAME
    }
    if (laneName == '') {
        laneName = ((branchName in "staging,quality,master") || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
    }
    if (versionSuffix == '') {
        versionSuffix = (branchName == "master") ? '' :  env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
    }
    jplConfig.laneName = laneName
    jplConfig.versionSuffix = versionSuffix
}
