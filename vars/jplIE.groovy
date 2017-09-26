/**

  Integration Events (IE) management

  Parameters:
  * cfg jplConfig class object

  cfg usage:
  * cfg.ie.*

  Rules:
  - The Integration Event line should start with '@ie'
  - The event can have multiple parameters: "parameter1", "parameter2", etc.
  - Every parameter can have multiple options, starting with "+" or "-": "+option1 -option2"
    - If an option starts with "+" means the parameter "must have" the option
    - If an option starts with "-" means the option "should not be" in the parameter
  - You can't use an option after the command. It's mandatory to use a parameter

  * Example:
    "@ie command parameter1 +option1 -option2 parameter2 +option1 +option2 -option3"

  Commands:
  - "fastlane": use multiple fastlane lanes, at least one. You can add multiple parameters in each

    * Examples:
      "@ie fastlane develop"
      "@ie fastlane develop quality"
      "@ie fastlane develop -applivery +appetize quality +applivery -appetize"

  - "gradlew": use gradle wrapper tasks
  
    * Examples:
      "@ie gradlew clean assembleDebug"
   
*/
def call(cfg) {
    cfg.ie.ieCommitRawText = sh (
        script: "git log -n1|grep '@ie'||echo ''",
        returnStdout: true
    ).trim()
    if (cfg.ie.ieCommitRawText != "") {
        echo "Integration Events: ${cfg.ie.ieCommitRawText}"
        item = cfg.ie.ieCommitRawText.tokenize(" ")
        if (item.size() < 2) {
            error ("@ie: command missing")
        }
        if (item[0] != "@ie") {
            error("@ie: integration event event commit line should starts with '@ie'")
        }
        cfg.ie.commandName = item[1]
        // Parse parameters and options
        parameterIndex = -1
        currentItem = ""
        for (int i = 2; i < item.size(); i++) {
            currentItem = item[i]
            if (currentItem.startsWith("+") || currentItem.startsWith("-")) {
                if (parameterIndex == -1) {
                    error("@ie: missing first parameter")
                }
                cfg.ie.parameter[parameterIndex].option[optionIndex] = [:]
                cfg.ie.parameter[parameterIndex].option[optionIndex].name = currentItem.substring(1)
                cfg.ie.parameter[parameterIndex].option[optionIndex].status = currentItem.startsWith("+") ? "enabled" : "disabled";
                optionIndex++;
            }
            else {
                parameterIndex++;
                cfg.ie.parameter[parameterIndex] = [:]
                cfg.ie.parameter[parameterIndex].name = currentItem
                cfg.ie.parameter[parameterIndex].option = [:]
                optionIndex = 0
            }
        }
        // @ie summary
        echo "@ie command: ${cfg.ie.commandName}, parameters: ${cfg.ie.parameter}"

        // skip
        if (cfg.ie.commandName == "skip") {
            if (cfg.BRANCH_NAME == "develop") {
                currentBuild.result = 'ABORTED'
                error("@ie command 'skip': aborting build")
            }
            else {
                echo "@ie command 'skip': only works in 'develop' branch, you are in ${cfg.BRANCH_NME} branch"
            }
        }
    }
}
