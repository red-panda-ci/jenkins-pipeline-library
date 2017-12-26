/**
Docker image build

Parameters:

* cfg jplConfig class object
* String dockerImageName Name of the docker image, defaults to cfg.projectName
* String dockerImageTag Tag of the docker image, defaults to "latest"
* String dockerfilePath The path where the Dockerfile is placed, default to the root path of the repository

cfg usage:

* cfg.projectName
*/
def call(cfg, String dockerImageName = '', String dockerImageTag = 'latest', String dockerfilePath = './') {
    jplConfig.checkInitializationStatus(cfg)
    def app
    dockerImageName = (dockerImageName == '') ? cfg.projectName : dockerImageName
    dir(dockerfilePath) {
        app = docker.build("${dockerImageName}:${dockerImageTag}")
    }
    return app
}
