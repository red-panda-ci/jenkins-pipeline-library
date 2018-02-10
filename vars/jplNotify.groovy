/**
Notify using multiple methods: hipchat, slack, email

Parameters:

* cfg jplConfig class object
* String summary The summary of the message (blank to use defaults)
* String message The message itself (blank to use defaults)

cfg usage:

* cfg.recipients.*
*/
def call(cfg, String summary = '', String message = '') {
    jplConfig.checkInitializationStatus(cfg)
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
    summary = jplBuild.summary(summary)
    message = jplBuild.description(message)
    hipchatRooms = cfg.recipients.hipchat
    slackChannels = cfg.recipients.slack
    emailRecipients = cfg.recipients.email
    if (jplBuild.resultStatus() == 'SUCCESS') {
        slackChannels = ''
        emailRecipients = ''
    }
    if (hipchatRooms != "") {
        hipchatSend color: hipchatColor, failOnError: true, room: hipchatRooms, message: "${summary}\n${message}", notify: true, server: 'api.hipchat.com', v2enabled: true
    }
    if (slackChannels != "") {
        slackSend channel: slackChannels, color: slackColor, message: "${summary}\n${message}"
    }
    if (emailRecipients != "") {
        //def to = emailextrecipients([[$class: 'DevelopersRecipientProvider'],[$class: 'CulpritsRecipientProvider'],[$class: 'UpstreamComitterRecipientProvider'],[$class: 'FirstFailingBuildSuspectsRecipientProvider'],[$class: 'FailingTestSuspectsRecipientProvider']])
        //mail to: to, cc: emailRecippients, subject: summary, body: message
        emailext(body: message, mimeType: 'text/html',
            replyTo: '$DEFAULT_REPLYTO', subject: summary,
            to: emailRecipients, recipientProviders: [[$class: 'DevelopersRecipientProvider'],[$class: 'CulpritsRecipientProvider'],[$class: 'UpstreamComitterRecipientProvider'],[$class: 'FirstFailingBuildSuspectsRecipientProvider'],[$class: 'FailingTestSuspectsRecipientProvider'], [$class: 'RequesterRecipientProvider']])
    }
}
