#!groovy

class jpl {
    def gitPromote = "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- "
    def to = emailextrecipients([[$class: 'DevelopersRecipientProvider'],[$class: 'CulpritsRecipientProvider'],[$class: 'UpstreamComitterRecipientProvider'],[$class: 'FirstFailingBuildSuspectsRecipientProvider'],[$class: 'FailingTestSuspectsRecipientProvider']])
}

