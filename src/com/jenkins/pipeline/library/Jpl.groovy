package com.jenkins.pipeline.library

class Jpl {
    def gitPromoteCmd = "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- "
    def steps

    Jpl (steps) {
        this.steps = steps
    }

    def gitPromote(upstream,downstream) {
        steps.sh "echo ${gitPromoteCmd}"
        steps.sh "${gitPromoteCmd} -m 'Merge from ${upstream} with Jenkins' ${downstream} quality"
    }
}
