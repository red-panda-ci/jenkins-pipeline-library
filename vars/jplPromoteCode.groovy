/**

Promote code on release

Merge code from upstream branch to downstream branch, then make "push" to the repository

The function uses "git promote" script of https://github.com/red-panda-ci/git-promote

Parameters:

* cfg jplConfig class object
* String updateBranch The branch "source" of the merge
* String downstreamBranch The branch "target" of the merge

*/
def call(cfg, String upstreamBranch, String downstreamBranch) {
    sh "grep '\\+refs/heads/\\*:refs/remotes/origin/\\*' .git/config -q || git config --add remote.origin.fetch +refs/heads/*:refs/remotes/origin/*"
    jplCheckout(cfg)
    sh "wget -O - https://raw.githubusercontent.com/red-panda-ci/git-promote/master/git-promote | bash -s -- -m 'Merge from ${upstreamBranch} with Red Panda jpl' ${upstreamBranch} ${downstreamBranch}"
}
