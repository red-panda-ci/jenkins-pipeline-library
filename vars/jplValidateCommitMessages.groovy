/**

Validate commit messages on PR's using https://github.com/willsoto/validate-commit project

- Check a concrete quantity of commits on the actual PR on the code repository
- Breaks the build if any commit don'w follow the preset rules

Parameters:

* cfg jplConfig class object
* int quantity Number of commits to check
* String preset Preset to use in validation
  Should be one of the supported presets of the willsoto validate commit project:
  - angular
  - atom
  - eslint
  - ember
  - jquery
  - jshint

cfg usage:

* cfg.commitValidation.*

*/
def call(cfg, quantity = null, preset = null) {
    script {
        quantity = (quantity == null) ? cfg.commitValidation.quantity : quantity
        preset = (preset == null) ? cfg.commitValidation.preset : preset
        sh "git log --oneline --no-merges -n ${quantity}|cut -c9-|${cfg.dockerFunctionPrefix} -e PRESET='${preset}' madoos/node-commit-validator:latest"
    }
}
