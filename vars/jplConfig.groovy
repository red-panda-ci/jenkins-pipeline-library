/**

  Global config variables

*/
class jplConfig implements Serializable {
    private String laneName
    private String versionSuffix

    def setLaneName(value) {
        this.laneName = value
    }
    def getLaneName(value) {
        return this.laneName
    }
    
    def setVersionSuffix(value) {
        this.versionSuffix = value
    }
    def getVersionSuffix(value) {
        return this.versionSuffix
    }

    // Constructor
    jplConfig () {
        this.laneName = (env.BRANCH_NAME == "master") ? '' :  env.BUILD_NUMBER + "-" + env.BRANCH_NAME.tokenize("/")[0]
        this.versionSuffix = ((env.BRANCH_NAME in "staging,quality,master") || env.BRANCH_NAME.startsWith('release/')) ? env.BRANCH_NAME.tokenize("/")[0] : 'develop'
    }
}
