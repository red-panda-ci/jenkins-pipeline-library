/**

  Close release (Branch "release/*")

  Merge code from release/vX.Y.Z to "master" and "develop", then "push" to the repository.
  Create new tag with "vX.Y.Z" to the commit

  The function uses "git promote" script

  Fails if your repository is not in a "release/*" branch

*/
def call() {
    script {
        if (!env.BRANCH_NAME.startsWith('release/*')) {
            error "The reposisoty must be on release/* branch"
        }
        item = env.BRANCH_NAME.split("/")
        tag = item[1]
    }
    // Promote to develop
    sh "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- -m 'Merge from ${env.BRANCH_NAME} with Jenkins' ${env.BRANCH_NAME} develop"
    // Release TAG and delete release branch
    sh 'git checkout master'
    sh 'git pull'
    sh 'git tag ' + tag + ' -m "Release ' + tag + '"'
    sh 'git push --tags'
    sh 'git push origin :' + env.BRANCH_NAME
}
