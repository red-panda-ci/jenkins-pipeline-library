#!groovy

class jpl implements Serializable {
    def gitPromoteCmd = "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- "
    def gitPromote(upstream,downstream) {
        sh "echo ${gitPromoteCmd}"
        sh "${gitPromoteCmd} -m 'Merge from ${upstream} with Jenkins' ${downstream} quality"
    }
}
