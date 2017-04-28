/**

  Promote code on release

  Merge code from upstream branch to downstream branch, then make "push" to the repository
  When the code is safe in the repository, launch the job with the same name of the downstream branch and wait for the build result

  The function uses "git promote" script

  Parameters:
  * String updateBranch The branch "source" of the merge
  * String downstreamBranch The branch "target" of the merge

*/
def call(String upstreamBranch,String downstreamBranch) {
    timestamps {
        ansiColor('xterm') {
          checkout scm
          sh 'git submodule update --init'
          sh "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- -m 'Merge from ${upstreamBranch} with Jenkins' ${upstreamBranch} ${downstreamBranch}"
          build (job: downstreamBranch, wait: true)
        }
    }
}
