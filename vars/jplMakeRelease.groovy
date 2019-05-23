/**

Make new release automatically

The function will:

- Calculate the next release tag using "get-next-release-number" docker command https://github.com/kairops/dc-get-next-release-number
- Build the changelog
- Append a new line in "jpl-makeRelease.log" file with the release information (tag name and timestamp)
- Publish the changes to the repository (`git push`) on the develop branch
- Publish the release tag to the repository using Jenkins SSH credentials

Abort the build if:

- The repository is on the "develop" branch. Or...
- The promoteBuild.enabled flag is not true

You can use this function with a branch named "release/next", so it will do all the job for you when you do a push to the repository.

Aditionally, the function will append a line to the "jpl-makeRelease.log" file with the release tag and the curremt timestam

Parameters:
* cfg jplConfig class object

cfg usage:

* cfg.makeReleaseCredentialsID
* cfg.notify
* cfg.recipients
*/
def call(cfg, boolean promoteBuild = false) {
    if (cfg.BRANCH_NAME == 'develop') {
        currentBuild.result = 'ABORTED'
        error ('jplMakeRelease: The repository cannot be on the "develop" branch')
    }
    if (!promoteBuild) {
        currentBuild.result = 'ABORTED'
        error ('jplMakeRelease: The build cannot be promoted because the cfg.poromoteBuild.enabled flag is not disables')
    }
    nextReleaseNumber = sh (script: "kd get-next-release-number .", returnStdout: true).trim()
    nextReleaseBranch="release/" + nextReleaseNumber
    echo "Building next release: ${nextReleaseNumber}"
    sshagent (credentials: [cfg.makeReleaseCredentialsID]) {

        // Release build (does not require remote connection)
        // It should be placed in a Docker Command https://github.com/kairops/docker-command-launcher in the future
        sh """
        git clean -f -d
        git config --local user.name 'Jenkins'
        git config --local user.email 'jenkins@ci'
        git branch ${nextReleaseBranch} -D || true
        git checkout -b ${nextReleaseBranch}
        echo \"Build release ${nextReleaseNumber} at `date`\" >> jpl-makeRelease.log
        git add jpl-makeRelease.log
        git commit -m \"Build: Update Auto Release Log adding ${nextReleaseNumber} info with JPL\"
        git tag -a ${nextReleaseNumber} -m \"Release ${nextReleaseNumber}\" `git rev-list --no-merges -n 1 ${nextReleaseBranch}`
        echo && kd git-changelog-generator . > CHANGELOG.md
        git add CHANGELOG.md
        git commit -m \"Docs: Generate ${nextReleaseNumber} changelog with JPL\"
        """

        // Ensure "develop" branch is updated and merge the release branch to develop
        // Then push to the repository the develop branch and the tag
        //    (remote connection to repository required)
        sh """
        git checkout develop
        git branch --set-upstream-to=origin/develop
        git pull
        git merge ${nextReleaseBranch} -m 'Merge from release with JPL'
        git push -u origin develop
        git push --tags
        """

        // Empty workspace directory for the next release
        deleteDir()
    }
}
