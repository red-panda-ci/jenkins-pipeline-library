/**

  Global map

*/
def call (jplConfig) {
    echo env.JOB_NAME
    echo jplConfig.projectName
    echo jplConfig.targetPlatform
}
