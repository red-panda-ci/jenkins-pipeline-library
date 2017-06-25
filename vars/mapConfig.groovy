/**

  Global map

*/
public String env
public String projectName

def call () {
    echo env.JOB_NAME
    echo jplConfig.projectName
    echo jplConfig.targetPlatform
}
