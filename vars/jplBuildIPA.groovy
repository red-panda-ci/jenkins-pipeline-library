/**
Build IPA with Fastlane based on jpl project configuration

Parameters:

* cfg jplConfig class object
* string command What's' the command to be executed in the build
Example: "fastlane test"

cfg usage:

* cfg.archivePattern
*/
def call(cfg, String command='') {
    jplConfig.checkInitializationStatus(cfg)
    // Build default
    if (command == '') {
        this.buildIPA(cfg,"fastlane ${cfg.laneName}")
    }
    else {
        this.buildIPA(cfg,command)
    }
}

def buildIPA(cfg, command) {
    sh '[ -f Gemfile ] && bundle install || true'
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
