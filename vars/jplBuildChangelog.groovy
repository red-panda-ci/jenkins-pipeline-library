/**
  Build changelog file based on the commit messages

  Parameters:

  * cfg jplConfig class object
  * String format Changelog format: "md" or "html"          (defaults to "md")
  * String filename Changelog file name                     (defaults to "CHANGELOG.md")

  cfg usage:

  * cfg.BRNACH_NAME

  This function need the installation of "kd" script of docker-command-launcher
  Review https://github.com/kairops/docker-command-launcher project

*/
def call(cfg, String format = 'md', String filename = 'CHANGELOG.md') {
    jplConfig.checkInitializationStatus(cfg)
    // Build changelog report of the build
    if (format == "md") {
        sh "kd kairops/dc-git-changelog-generator . > ${filename}"
    }
    else {
        sh "kd kairops/dc-git-changelog-generator . | kd md2html - > ${filename}"
    }
}
