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
//        this.laneName = "develop"
//        this.versionSuffix = ""
    }
}
