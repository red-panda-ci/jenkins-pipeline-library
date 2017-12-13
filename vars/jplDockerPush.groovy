/**
Docker image build & push to registry

Parameters:

* cfg jplConfig class object
* String dockerImageName Name of the docker image, defaults to cfg.projectName
* String dockerImageTag Tag of the docker image, defaults to "latest"
* String dockerfilePath The path where the Dockerfile is placed, default to the root path of the repository
* String dockerRegistryURL The URL of the docker registry. Defaults to https://registry.hub.docker.com
* String dockerRegistryJenkinsCredentials Jenkins credentials for the docker registry

cfg usage:

* cfg.projectName
*/
def call(cfg, String dockerImageName = "", String dockerImageTag = "latest", String dockerfilePath = "./", String dockerRegistryURL = "", String dockerRegistryJenkinsCredentials = "") {

    script {
        def app
        dockerRegistryURL = (dockerRegistryURL == '') ? 'https://registry.hub.docker.com' : dockerRegistryURL
        dockerImageName = (dockerImageName == '') ? cfg.projectName : dockerImageName
        app = jplDockerBuild(cfg, dockerImageName, dockerImageTag, dockerfilePath)
        docker.withRegistry(dockerRegistryURL, dockerRegistryJenkinsCredentials) {
            app.push(dockerImageTag)
        }
    }
}
