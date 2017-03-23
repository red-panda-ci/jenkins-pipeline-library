/**
  Notify on different mediums

  Parameters:
  String hipchatRooms Specify what hipchat rooms to notify to (if empty, don't notify on hipchat)
  String slackChannels Specify what slack channels to notify to (if empty, don't notify on slack)
  String emailRecipients Specify what email recipients to notify to (if empty, don't notify on mail)

 */
def call(String hipchatRooms = "",String slackChannels = "",String emailRecipients = "") {

    script {
        message = "Job ${env.JOB_NAME} [#${env.BUILD_NUMBER}] finished with ${currentBuild.result} (branch ${env.BRANCH_NAME})"
        switch (currentBuil.result) {
            case 'SUCCESS':
                hipchatColor = 'GREEN'
                slackColor = 'good'
                break;
            case 'FAILURE':
                hipchatColor = 'RED'
                slackColor = 'danger'
                break;
            default: // Include "UNSTABLE", "ABORTED" and others 
                hipchatColor = 'GRAY'
                slackColor = 'warning'
                break;
        }
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
