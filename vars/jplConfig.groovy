/**

  Global config variables

*/
class jplConfig implements Serializable {
    public String laneName
    public String versionSuffix
    private String branchName

    def initialize(env) {

        if (env.BRANCH_NAME == null) {
            this.branchName = 'develop'
        }
        else {
            this.branchName = env.BRANCH_NAME
        }
        this.laneName = ((branchName in "staging,quality,master") || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
        this.versionSuffix = (branchName == "master") ? '' :  env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
    }
}
