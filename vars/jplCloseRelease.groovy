/**
Close release (Branch "release/*")

Merge code from release/vX.Y.Z to "master" and "develop", then "push" to the repository.
Create new tag with "vX.Y.Z" to the commit

The function uses "git promote" script

Fails if your repository is not in a "release/*" branch

Parameters:
* cfg jplConfig class object

cfg usage:

* cfg.notify
* cfg.recipients
*/
def call(cfg) {
    if (!cfg.promoteBuild.enabled) {
        echo "jplCloseRelease: you don't had confirmed the build"
        return false
    }
    script {
        if (!cfg.BRANCH_NAME.startsWith('release/')) {
            error "The reposisoty must be on release/* branch"
        }
        tag = cfg.BRANCH_NAME.split("/")[1]
    }
    sh "grep '\\+refs/heads/\\*:refs/remotes/origin/\\*' .git/config -q || git config --add remote.origin.fetch +refs/heads/*:refs/remotes/origin/*"
    sh "git fetch -p"
    // Build and commit changelog
    if (cfg.changelog.enabled) {
        sh 'git tag ' + tag + ' -m "Release ' + tag + '" `git rev-list --no-merges -n 1 ' + cfg.BRANCH_NAME + '`'
        sh "mkdir -p ci-scripts/reports"
        jplBuildChangelog(cfg, tag, 'md', 'ci-scripts/reports/CHANGELOG.md')
        sh "tail -n +7 ci-scripts/reports/CHANGELOG.md > CHANGELOG.md"
        fileOperations([fileDeleteOperation(includes: 'ci-scripts/reports/CHANGELOG.md')])
        sh 'git tag ' + tag + ' -d; git add CHANGELOG.md; git commit -m "Build: Update CHANGELOG.md to ' + tag + ' with Red Panda JPL"'
    }

    // Promote to master
    sh "wget -O - https://raw.githubusercontent.com/red-panda-ci/git-promote/master/git-promote | bash -s -- -m 'Merge from ${cfg.BRANCH_NAME} with Red Panda JPL' ${cfg.BRANCH_NAME} master"
    // Promote to develop
    sh "wget -O - https://raw.githubusercontent.com/red-panda-ci/git-promote/master/git-promote | bash -s -- -m 'Merge from ${cfg.BRANCH_NAME} with Red Panda JPL' ${cfg.BRANCH_NAME} develop"
    // Release TAG from last non-merge commit of the branch
    sh 'git tag ' + tag + ' -m "Release ' + tag + '" `git rev-list --no-merges -n 1 ' + cfg.BRANCH_NAME + '`'
    sh 'git push --tags'
    // Delete release branch from origin
    sh 'git push origin :' + cfg.BRANCH_NAME
    // Notify
    if (cfg.notify) {
        jplNotify(cfg,'New release ${tag} finished',jplBuild.description())
    }
}
