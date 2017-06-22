/**

  Global config variables

*/
class jplConfig implements Serializable {
    // Public properties (config values)
    public String laneName
    public String versionSuffix
    public String targetPlatform
    
    // Maybe can't use local variables because it's serializable (?)
    private String branchName

    def initialize(env,targetPlatform) {
        if (env.BRANCH_NAME == null) {
            this.branchName = 'develop'
        }
        else {
            this.branchName = env.BRANCH_NAME
        }
        this.laneName = ((branchName in ["staging","quality","master"]) || branchName.startsWith('release/')) ? branchName.tokenize("/")[0] : 'develop'
        this.versionSuffix = (branchName == "master") ? '' :  "rc" + env.BUILD_NUMBER + "-" + branchName.tokenize("/")[0]
        this.targetPlatform = targetPlatform
    }
}
