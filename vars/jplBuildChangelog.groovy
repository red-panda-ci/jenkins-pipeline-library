/**

  Build changelog file

  Parameters:

  * cfg jplConfig class object
  * String tagFrom Repository tag wich changelog starts     (defaults to "HEAD")
  * String format Changelog format: "md" or "html"          (defaults to "md")
  * String filename Changelog file name                     (defaults to "CHANGELOG.md")

  cfg usage:

  * cfg.BRNACH_NAME

  Build and optionally archive changelog based on the commit messages

*/
def call(cfg, String tagFrom = 'HEAD', String format = 'md', String filename = 'CHANGELOG.md') {
    script {
        // Build, archive and attach HTML changelog report to the build
        repositoryUrl = sh (
            script: "git ls-remote --get-url",
            returnStdout: true
        )
        .trim()
        .replace('git@github.com:','https://github.com/')
        .replace('git@bitbucket.org:','https://bitbucket.org/')
        sh "git log --format='%B%n-hash-%n%H%n-gitTags-%n%d%n-committerDate-%n%ci%n------------ _¿? ------------' ${tagFrom} --no-merges|${cfg.dockerFunctionPrefix} -e COMMIT_DELIMITER='------------ _¿? ------------' -e PRESET='eslint' -e GIT_URL='${repositoryUrl}' -e FORMAT='${format}' redpandaci/node-pipe-changelog-generator > ${filename}"
    }
}
