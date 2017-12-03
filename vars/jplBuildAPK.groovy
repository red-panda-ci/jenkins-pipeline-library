/**
Build APK with Fastlane within docker into Jenkins, based on jpl project configuration

Parameters:

* cfg jplConfig class object
* string command What is the command to be executed in the build
Example: "./gradlew clean assembleDebug"

cfg usage:

* cfg.projectName
* cfg.laneName
* cfg.versionSuffix

Notes:

* Marked as DEPRECATED by jplBuild on 2017-09-02. Removed on a future release.
*/
def call(cfg, String command='') {
    // Build Docker image
    buildDockerImage(cfg)

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
                this.buildAPK(cfg,command)
            }
        }
        else {
            this.buildAPK(cfg,"fastlane ${cfg.laneName} versionSuffix:${cfg.versionSuffix}")
        }
    }
    else {
        this.buildAPK(cfg,command)
    }
}

def buildDockerImage(cfg) {
    if (fileExists('Dockerfile')) {
        deleteDockefileAfterBuild = false
    }
    else {
        deleteDockerfileAfterBuild = true
        writeFile file: 'Dockerfile', text: 'FROM redpandaci/jpl-android-base\nRUN ( sleep 4 && while [ 1 ]; do sleep 1; echo y; done ) | android update sdk --no-ui --force -a --filter ' + cfg.androidPackages + '\n'
    }
    if (fileExists('.dockerignore')) {
        deleteDockerignoreAfterBuild = false
        dockerignoreContent = readFile '.dockerignore'
    }
    else {
        deleteDockerignoreAfterBuild = true
        dockerignoreContent = ''
    }
    writeFile file: '.dockerignore', text: dockerignoreContent + '\n.gradle\n.android\n.git\n'
    docker.build("jpl-android:${cfg.projectName}")
    if (deleteDockerfileAfterBuild) {
        fileOperations([fileDeleteOperation(includes: 'Dockerfile')])
    }
    if (deleteDockerignoreAfterBuild) {
        fileOperations([fileDeleteOperation(includes: '.dockerignore')])
    }
}

def buildAPK(cfg,command) {
    docker.image("jpl-android:${cfg.projectName}").inside {
        sh 'mkdir -p .android .gradle; [ ! -f .android/debug.keystore ] && keytool -genkey -v -keystore .android/debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "C=US, O=Android, CN=Android Debug" || true'
        sh "export HOME=`pwd` ANDROID_SDK_HOME=`pwd` GRADLE_USER_HOME=`pwd`/.gradle; ${command}"
    }
    archiveArtifacts artifacts: '**/*DebugUnitTest.exec', fingerprint: true, allowEmptyArchive: true
    archiveArtifacts artifacts: cfg.archivePattern, fingerprint: true, allowEmptyArchive: true
}
