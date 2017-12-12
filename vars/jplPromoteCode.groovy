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
    // Download ci-scripts
    jplConfig.downloadScripts(cfg)
    sh "grep '\\+refs/heads/\\*:refs/remotes/origin/\\*' .git/config -q || git config --add remote.origin.fetch +refs/heads/*:refs/remotes/origin/*"
    jplCheckoutSCM(cfg)
    sh "ci-scripts/.jpl-scripts/bin/git-promote.sh -m 'Merge from ${upstreamBranch} with Red Panda JPL' ${upstreamBranch} ${downstreamBranch}"
}
