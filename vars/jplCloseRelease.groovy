/**
Close release (Branches "release/v*" or "hotfix/v*")

Merge code from release/vX.Y.Z to "master" and "develop", then "push" to the repository.
Create new tag with "vX.Y.Z" to the commit

The function uses "git promote" script

Fails if your repository is not in a "release/v*" nor "hotfix/v*" branch

Parameters:
* cfg jplConfig class object

cfg usage:

* cfg.notify
* cfg.recipients
*/
def call(cfg) {
    jplConfig.checkInitializationStatus(cfg)
    if (!cfg.promoteBuild.enabled) {
        echo "jplCloseRelease: you don't had confirmed the build"
        return false
    }
    if (!(cfg.BRANCH_NAME.startsWith('release/v') || cfg.BRANCH_NAME.startsWith('hotfix/v'))) {
        error "The reposisoty must be on release/v* or hotfix/v* branch"
    }
    jplCheckoutSCM(cfg)
    tag = cfg.BRANCH_NAME.split("/")[1]
    tagMessage = cfg.BRANCH_NAME.startsWith('release/v') ? 'Release' : 'Hotfix'
    // Download ci-scripts
    jplConfig.downloadScripts(cfg)
    // Build and commit changelog
    if (cfg.changelog.enabled) {
        sh "git tag ${tag} -m '${tagMessage} ${tag}' `git rev-list --no-merges -n 1 ${cfg.BRANCH_NAME}`"
        jplBuildChangelog(cfg, 'md', 'CHANGELOG.md')
        sh """
        git tag ${tag} -d
        git add CHANGELOG.md
        git commit -m "Build: Update CHANGELOG.md to ${tag} with Red Panda JPL"
        """
    }

    // Promote to master
    sh "ci-scripts/.jpl-scripts/bin/git-promote.sh -m 'Merge from ${cfg.BRANCH_NAME} with Red Panda JPL' ${cfg.BRANCH_NAME} master"
    // Promote to develop
    sh "ci-scripts/.jpl-scripts/bin/git-promote.sh -m 'Merge from ${cfg.BRANCH_NAME} with Red Panda JPL' ${cfg.BRANCH_NAME} develop"
    // Release TAG from last non-merge commit of the branch
    sh """
    git tag ${tag} -m "${tagMessage} ${tag}" `git rev-list --no-merges -n 1 ${cfg.BRANCH_NAME}`
    git push --tags
    """
    // Delete release branch from origin
    sh "git push origin :${cfg.BRANCH_NAME}"
    // Notify
    if (cfg.notify) {
        jplNotify(cfg,'New release ${tag} finished',jplBuild.description())
    }
}
