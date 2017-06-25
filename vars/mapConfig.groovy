/**

  Global map

*/
public String env
public String projectName

def call () {
    echo cfg.projectName
    echo cfg.targetPlatform
}
