/**
Build IPA with Fastlane based on jpl project configuration

Parameters:

* cfg jplConfig class object
* string command What's' the command to be executed in the build
Example: "fastlane test"

cfg usage:

* cfg.archivePattern
* cfg.ie.*
* cfg.versionSuffix
*/
def call(cfg, String command='') {
    jplConfig.checkInitializationStatus(cfg)
    // Build default
    if (command == '') {
        if (cfg.ie.commandName == "fastlane") {
            for (int i = 0; i < cfg.ie.parameter.size(); i++) {
                parameter = cfg.ie.parameter[i]
                command = "fastlane ${parameter.name} versionSuffix:${cfg.versionSuffix}"
                for (int j = 0; j < parameter.option.size(); j++) {
                    option = parameter.option[j]
                    command = "${command} ${option.name}:${option.status}"
                }
                echo "Execute @ie on lane '${parameter.name}' with command '${command}'"
                this.buildIPA(cfg,command)
            }
        }
        else {
            this.buildIPA(cfg,"fastlane ${cfg.laneName} versionSuffix:${cfg.versionSuffix}")
        }
    }
    else {
        this.buildIPA(cfg,command)
    }
}

def buildIPA(cfg, command) {
    sh 'git clean -fd; git reset --hard HEAD'
    sh "${command}"
    if (command == 'fastlane test') {
        step([$class: 'JUnitResultArchiver', allowEmptyResults: true, testResults: 'fastlane/test_output/report.junit'])
    }
    else if (cfg.archivePattern != '') {
        archiveArtifacts artifacts: cfg.archivePattern, fingerprint: true, allowEmptyArchive: true
    }
    sh 'git checkout .'
}
