/**

  Global config variables

*/
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

def call() {
    this.laneName = "test.lanename"
    this.versionSuffix = "test.versionsuffix"
//    echo env.BRANCH_NAME
}
