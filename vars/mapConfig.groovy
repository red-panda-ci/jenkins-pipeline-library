/**

  Global map

*/
public String env
public String projectName

def call () {
    echo env.JOB_NAME
    echo cfg.projectName
    echo cfg.targetPlatform
}
