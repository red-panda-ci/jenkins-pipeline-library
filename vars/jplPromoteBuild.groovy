/**

  Promote build to next steps, waiting for user input

  Parameters:
  * cfg jplConfig class object
  * String message User input message
  * String message User input description

*/
def call(cfg, message = "Promote Build", description = "Check to promote the build, leave uncheck to finish the build without promote") {
    timestamps {
        ansiColor('xterm') {
            try {
                cfg.promoteBuild = false
                timeout(time: 8, unit: 'HOURS') {
                    cfg.promoteBuild = input(
                        id: 'promoteBuild', message: message,
                        parameters: [[$class: 'BooleanParameterDefinition', defaultValue: false, description: description, name: 'promoteBuild']]
                    )
                }
            } catch(err) { // timeout reached or input false
                echo "Timeout reached / User aborted"
            }
            if (!cfg.promoteBuild) {
                echo "Promote this build"
            }
            else {
                echo "Don't promote this build"
            }
        }
    }
}
