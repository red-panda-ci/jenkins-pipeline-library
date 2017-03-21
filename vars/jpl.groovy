#!groovy

class jpl {
    private array emailRecipients
    private string emailCopy

    def gitPromote () {
        return "wget -O - https://raw.githubusercontent.com/pedroamador/git-promote/master/git-promote | bash -s -- "
    }
    def setEmailRecipients (value) {
        emailRecipients = value

    }
    def setEmailCopy (value) {
        emailCopy = value
    }

    def notifyEmail () {
        mail to: emailRecipients, cc: emailCopy, subject: "Job ${env.JOB_NAME} [${env.BUILD_NUMBER}] finished with ${currentBuild.result}", body: "See ${env.BUILD_URL}/console"
    }
}
