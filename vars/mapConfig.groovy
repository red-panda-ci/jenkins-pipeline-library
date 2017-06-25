/**

  Global map

*/
public String env
public String projectName

def call () {
    echo jplConfig.projectName
    echo jplConfig.targetPlatform
}
