/**

  Notify using multiple methods: hipchat, slack, email

  Parameters:
  * String hipchatRooms Specify what hipchat rooms to notify to (if empty, don't notify on hipchat)
  * String slackChannels Specify what slack channels to notify to (if empty, don't notify on slack)
  * String emailRecipients Specify what email recipients to notify to (if empty, don't send any mail)

*/
def call(String hipchatRooms = "",String slackChannels = "",String emailRecipients = "") {

    script {
        switch (currentBuild.result) {
            case 'ABORTED':
                hipchatColor = 'GRAY'
                slackColor = 'warning'
                break;
            case 'UNSTABLE':
                hipchatColor = 'YELLOW'
                slackColor = 'warning'
                break;
            case 'FAILURE':
                hipchatColor = 'RED'
                slackColor = 'danger'
                break;
            default: // SUCCESS and null
                hipchatColor = 'GREEN'
                slackColor = 'good'
                break;
        }
        if (currentBuild.result == null) {
            resultStatus = 'SUCCESS'
        }
        else {
            resultStatus = currentBuild.result
        }
        if (env.BRANCH_NAME == null) {
            branchInfo = ""
        }
        else {
            branchInfo = " (branch ${env.BRANCH_NAME})"
        }
        message = "Job [${env.JOB_NAME}] [#${env.BUILD_NUMBER}] finished with ${resultStatus}${branchInfo}"
        if (hipchatRooms != "") {
            hipchatSend color: hipchatColor, failOnError: true, room: hipchatRooms, message: message, notify: true, server: 'api.hipchat.com', v2enabled: true
        }
        if (hipchatRooms != "") {
            slackSend channel: slackChannels, color: slackColor, message: message
        }
        if (emailRecipients != "") {
            def to = emailextrecipients([[$class: 'DevelopersRecipientProvider'],[$class: 'CulpritsRecipientProvider'],[$class: 'UpstreamComitterRecipientProvider'],[$class: 'FirstFailingBuildSuspectsRecipientProvider'],[$class: 'FailingTestSuspectsRecipientProvider']])
            mail to: to, cc: emailRecipients, subject: message, body: "Details on ${env.BUILD_URL}/console"
        }
    }
}
