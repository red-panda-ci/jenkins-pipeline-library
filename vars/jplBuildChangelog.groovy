/**
  Build changelog file based on the commit messages

  You can build the changelog between two commits, tags or branches if you use range format "v1.1.0...v1.0.0"
  If you don fill the parameter then the "from HEAD to beginning" range is used

  Parameters:

  * cfg jplConfig class object
  * String range Commit range: tags, commits or branches    (defaults to "HEAD")
  * String format Changelog format: "md" or "html"          (defaults to "md")
  * String filename Changelog file name                     (defaults to "CHANGELOG.md")

  cfg usage:

  * cfg.BRNACH_NAME
*/
def call(cfg, String range = 'HEAD', String format = 'md', String filename = 'CHANGELOG.md') {
    jplConfig.checkInitializationStatus(cfg)
    // Check firstTag range
    if (cfg.changelog.firstTag != "") {
        if (cfg.changelog.firstTag.startsWith("...")) {
            range = range + cfg.changelog.firstTag
        } else {
            range = range + "..." + cfg.changelog.firstTag
        }
    }
    // Build changelog report to the build
    repositoryUrl = sh (
        script: "git ls-remote --get-url",
        returnStdout: true
    )
    .trim()
    .replace('git@github.com:','https://github.com/')
    .replace('git@bitbucket.org:','https://bitbucket.org/')
    .replace('git@gitlab.com:','https://gitlab.com/')
    if (repositoryUrl.endsWith(".git")) {
        repositoryUrl = repositoryUrl.substring(0, repositoryUrl.length() - 4)
    }
    if (repositoryUrl.startsWith("https://github.com")) {
        repositoryUrl = repositoryUrl + "/commit"
    } else if (repositoryUrl.startsWith("https://bitbucket.org")) {
        repositoryUrl = repositoryUrl + "/commits"
    } else {
        repositoryUrl = repositoryUrl + "/commit"
    }
    if (format == "md") {
        sh "docker run --rm -i -v `pwd`:`pwd`:ro -w `pwd` kairops/dc-git-changelog-generator > ${filename}"
    }
    else {
        sh "docker run --rm -i -v `pwd`:`pwd`:ro -w `pwd` kairops/dc-git-changelog-generator | docker run --rm -i kairops/dc-md2html - > ${filename}"
    }
}
