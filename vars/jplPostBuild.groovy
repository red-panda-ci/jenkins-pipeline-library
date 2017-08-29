/**

  Post build tasks

  Parameters:
  * cfg jplConfig class object

  cfg usage:
  - cfg.targetPlatform
  - cfg.notify
  - cfg.jiraProjectKey

*/
def call(cfg) {
    if (cfg.notify) {
        jplNotify(cfg)
    }
}
