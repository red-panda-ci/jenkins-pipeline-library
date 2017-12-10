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

def buildAPK(cfg, command) {
    def nodeData = [:]
    def dockerImage

    nodeData.jenkinsWorkspace = sh (script: 'pwd', returnStdout: true).trim()
    if (!cfg.flags.isAndroidImageBuilded) {
            nodeData.systemUserId    = sh (script: 'id -u', returnStdout: true).trim()
            nodeData.systemGroupId   = sh (script: 'id -g', returnStdout: true).trim()
            nodeData.systemUserName  = sh (script: 'whoami', returnStdout: true).trim()
            nodeData.systemGroupName = sh (script: "id -g -n ${nodeData.systemUserName}", returnStdout: true).trim()
        dockerImage = buildDockerImage(cfg, nodeData)
        cfg.flags.isAndroidImageBuilded = true
    }
    dockerImage.inside {
        sh 'mkdir -p .android; [ ! -f .android/debug.keystore ] && keytool -genkey -v -keystore .android/debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "C=US, O=Android, CN=Android Debug" || true'
        sh "${command}"
    }
    archiveArtifacts artifacts: '**/*DebugUnitTest.exec', fingerprint: true, allowEmptyArchive: true
    archiveArtifacts artifacts: cfg.archivePattern, fingerprint: true, allowEmptyArchive: true
}

def buildDockerImage(cfg, nodeData) {
    writeFile file: 'ci-scripts/.temp/android/Dockerfile', text: """# jpl Android Dockerfile
FROM redpandaci/jpl-android-base

WORKDIR /usr/local/android-sdk-linux
RUN echo y | sdkmanager ${cfg.androidPackages}

RUN groupadd ${nodeData.systemGroupName} -g ${nodeData.systemGroupId} || true
RUN useradd ${nodeData.systemUserName} -g ${nodeData.systemGroupId} -u ${nodeData.systemUserId} -d ${nodeData.jenkinsWorkspace} || true

ENV ANDROID_SDK_HOME=${nodeData.jenkinsWorkspace} \
    GRADLE_USER_HOME=${nodeData.jenkinsWorkspace}/.gradle

WORKDIR ${nodeData.jenkinsWorkspace}
"""
    return docker.build("jpl-android:${cfg.projectName}", '-f ci-scripts/.temp/android/Dockerfile .')
}
