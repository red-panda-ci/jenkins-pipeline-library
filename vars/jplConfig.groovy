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
}
