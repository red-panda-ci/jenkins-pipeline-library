/**

  Initialize jpl

*/
def call(String laneName = '',String versionSuffix = '') {
    if (laneName == '') {
        laneName = ((env.BRANCH_NAME in "staging,quality,master") || env.BRANCH_NAME.startsWith('release/')) ? env.BRANCH_NAME.tokenize("/")[0] : 'develop'
    }
    if (versionSuffix == '') {
        versionSuffix = (env.BRANCH_NAME == "master") ? '' :  env.BUILD_NUMBER + "-" + env.BRANCH_NAME.tokenize("/")[0]
    }
    jplConfig.laneName = laneName
    jplConfig.versionSuffix = versionSuffix
}
