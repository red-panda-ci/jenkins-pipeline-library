/**
 Promote code on release

 Merge code from upstream branch to downstream branch, then make "push" to the repository
 When the code is in the repository, launch the job and wait for result

 The function is using "git promote" script

 */
def call(String upstreamBranch,String downstreamBranch,String jobName) {    
    script {
        item = env.BRANCH_NAME.split("/")
        tag = item[1]        
    }
    checkout scm
    sh 'git submodule update --init'
    sh "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- 'Merge from ${upstreamBranch} with Jenkins' ${upstreamBranch} ${downstreamBranch}"
    build (job: jobName, wait: true)
}
