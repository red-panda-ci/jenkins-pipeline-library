/**

  Close release (Branch "release/*")

  Merge code from release/vX.Y.Z to "master" and "develop", then "push" to the repository.
  Create new tag with "vX.Y.Z" to the commit

  The function uses "git promote" script

  Fails if your repository is not in a "release/*" branch

*/
def call() {
    timestamps {
        ansiColor('xterm') {
            script {
                if (!env.BRANCH_NAME.startsWith('release/')) {
                    error "The reposisoty must be on release/* branch"
                }
                tag = env.BRANCH_NAME.split("/")[1]
            }
            // Promote to master
            sh "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- -m 'Merge from ${env.BRANCH_NAME} with Jenkins' ${env.BRANCH_NAME} master"
            // Promote to develop
            sh "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- -m 'Merge from ${env.BRANCH_NAME} with Jenkins' ${env.BRANCH_NAME} develop"
            // Release TAG from last non-merge commit of the branch
            sh 'git tag ' + tag + ' -m "Release ' + tag + '" `git rev-list --no-merges -n 1 ${env.BRANCH_NAME}`'
            sh 'git push --tags'
            // Delete release branch from origin
            sh 'git push origin :' + env.BRANCH_NAME
        }
    }
}
