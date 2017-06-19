/**

  Global config variables

*/
class jplConfig implements Serializable {
    private String laneName
    private String versionSuffix

    def setLaneName(value) {
        this.laneName = value
    }
    def getLaneName() {
        return this.laneName
    }
    
    def setVersionSuffix(value) {
        this.versionSuffix = value
    }
    def getVersionSuffix() {
        return this.versionSuffix
    }

    def initialize(env) {
        if (env.BRANCH_NAME == null) {
            branchName = 'develop'
        }
        else {
            branchName = env.BRANCH_NAME
        }
        this.laneName = ((branchName in "staging,quality,master") || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
        this.versionSuffix = (branchName == "master") ? '' :  env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
    }
}
