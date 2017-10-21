#!groovy
/**

  Docker image build & upload to registry

  Parameters:
  * cfg jplConfig class object
  * String dockerImageName Name of the docker image
  * String dockerRegistryURL The URL of the docker registry. Defaults to https://registry.hub.docker.com
  * String dockerRegistryJenkinsCredentials Jenkins credentials for the docker registry
  * String dockerfilePath The path where the Dockerfile is placed, default to the root path of the repository

*/
def call(cfg, String dockerImageName = '', String dockerRegistryURL = '', String dockerRegistryJenkinsCredentials = '', String dockerfilePath = "./") {

    script {
        def app
        dockerRegistryURL = (dockerRegistryURL == '') ? 'https://registry.hub.docker.com' : dockerRegistryURL
        dockerImageName = (dockerImageName == '') ? cfg.projectName : dockerImageName
        dir(dockerfilePath) {
            app = docker.build(dockerImageName)
        }
        docker.withRegistry(dockerRegistryURL, dockerRegistryJenkinsCredentials) {
            app.push("latest")
        }
    }
}
