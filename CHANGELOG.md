# Changelog

## v3.0.1 (2019-05-22)

### Update

* Use LF instead CRLF in jplMakeRelease ([b390957](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b390957))

### Fix

* Fix: Fix two variable name usage in jplMakeRelease ([6f0fea7](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6f0fea7))

### Build

* Fix jpl library version usage ([e62c36d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e62c36d))
* Update to jpl v3.0.0 ([d4531af](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d4531af))

## v3.0.0 (2019-05-21)

### Breaking

* Remove firstTag parameter in jplBuildChangelog ([16029cb](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/16029cb))
* Remove integration events from config ([aea0d02](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/aea0d02))
* Remove integration events feature ([130957e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/130957e))

### New

* Add jplMakeRelease function ([f546e1d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/f546e1d))
* Agent attachment ([7d27a6f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/7d27a6f))
* Add sgent script ([3cfa6f4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/3cfa6f4))

### Update

* Refactor jplCloseRelease ([6c14671](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6c14671))
* Enable jplCache for cache happy test ([2beab3b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/2beab3b))
* Add docker-command-launcher kd wrapper to test run script ([9dac342](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9dac342))

### Fix

* Docker command name ([0d8dbbb](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0d8dbbb))
* Use the right docker command to build the changelog ([f7471a4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/f7471a4))
* Typo in test stage ([d686f0e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d686f0e))

### Build

* Update CHANGELOG.md to v3.0.0 with Red Panda JPL ([46cc1c6](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/46cc1c6))
* Refactor Release Finish Pipeline stage ([421f601](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/421f601))
* Enable tests on agent node ([4d148f0](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/4d148f0))

## v2.9.1 (2019-05-16)

### Update

* Refactor git cache happy test ([82ddb99](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/82ddb99))

### Fix

* Commit generator typo ([9c07f93](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9c07f93))

### Build

* Update CHANGELOG.md to v2.6.1 with Red Panda JPL ([a2daf5b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/a2daf5b))

## v2.9.0 (2019-05-15)

### Upgrade

* Include test execution on all branches ([9451ae9](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9451ae9))

### Update

* Refactor build chnagelog execution ([6c9700f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6c9700f))
* Disable test execution on Jenkins temporary ([6743fc9](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6743fc9))
* Disable git cache by default ([36109c1](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/36109c1))

### Fix

* Allow empy test results ([1c02806](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1c02806))

### Build

* Update CHANGELOG.md to v2.9.0 with Red Panda JPL ([9604333](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9604333))
* Execute sonarqube analysis stage on master node ([87aa6f5](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/87aa6f5))
* Execute release finish stage on master node ([e0e0270](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e0e0270))

## v2.8.0 (2019-05-14)

### Update

* Use docker command changelog generator ([fb9f99f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/fb9f99f))

### Fix

* Use the right docker command with new changelog generator ([2ce873e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/2ce873e))

### Build

* Update CHANGELOG.md to v2.8.0 with Red Panda JPL ([e2d175e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e2d175e))
* Restrict ammount of memory used in the testsç ([98cd994](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/98cd994))

## v2.7.0 (2019-03-21)

### Update

* Add gitlab.com repository support to changelog generator ([eb1648c](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/eb1648c))

### Fix

* Add final slash to changelog generator URL forming ([728a510](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/728a510))

### Build

* Update CHANGELOG.md to v2.7.0 with Red Panda JPL ([801e485](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/801e485))

## v2.6.2 (2018-03-18)

### Fix

* Substring bug extracting release number ([b6026ea](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b6026ea))

### Build

* Update CHANGELOG.md to v2.6.2 with Red Panda JPL ([e9da189](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e9da189))
* Remove Jenkins stash/unstash from pipeline ([64a3a7f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/64a3a7f))

## v2.6.1 (2018-02-10)

### New

* Add hotfix support, with tests (fixes #67) ([7ae15dc](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/7ae15dc))

### Update

* Add URL to slack and hipchat notifications (fixes #68) ([dc1360f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/dc1360f))
* Remove versionSuffix behaviour (#69) ([1033205](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1033205))

### Build

* Update CHANGELOG.md to v2.6.1 with Red Panda JPL ([bdc27fe](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/bdc27fe))
* Disable test on release ([f8b3e66](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/f8b3e66))
* Don't break the build if there is not documentation update ([0c24f82](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0c24f82))
* Update close release tests ([694eb97](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/694eb97))
* Fix Jenkinsfile typo ([1461fc9](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1461fc9))

### Docs

* Update README.md and Jenkins doc help files ([b7acb92](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b7acb92))

## v2.6.0 (2018-01-18)

### New

* Add Gitter badge ([c9afed4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/c9afed4))

### Update

* Add gitter badge to readme template ([82ecde0](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/82ecde0))

### Fix

* Place submodule init in the right place for jplCheckoutSCM / jplStart ([06942e8](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/06942e8))

### Build

* Update CHANGELOG.md to v2.6.0 with Red Panda JPL ([e3e9e6a](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e3e9e6a))
* Update release number in sonar properties file ([7a9318d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/7a9318d))
* Add brtidge to network mode in docker-comppose ([6dbd3c2](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6dbd3c2))
* Avoid use bridge network mode forced in compose ([c4950cb](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/c4950cb))
* Add bridged network_model to compose ([df9af34](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/df9af34))
* Add more wait time before cli downloading ([0749fc6](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0749fc6))
* Remove unstash in promote build stage ([093a929](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/093a929))
* Configure stash/unstash within stages ([ed8ef0e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/ed8ef0e))

### Docs

* Update README.md ([f0ad43b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/f0ad43b))

## v2.5.0 (2018-01-04)

### New

* Add jplSignAPKTest (fixes #45) ([261ed4b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/261ed4b))
* Add jplPromoteCode test (fixes #46) ([18626fc](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/18626fc))

### Update

* Add 'bundle install' to IPA build ([acfd40b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/acfd40b))
* Convert recipients hash (hipchat, slack, email) to optional configurations ([497ec31](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/497ec31))
* Add enabled flag to 'Integration Events' (fixes #60) ([1e19593](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1e19593))
* Add '--pull' option to docker build image in jplBuildAPK (fixes #43) ([16200c4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/16200c4))
* Review versionSuffix behaviour (fixes #18) ([98b5ed1](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/98b5ed1))

### Fix

* Manage return value checking Gemfile in jplBuildIPA ([d403bd3](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d403bd3))

### Build

* Update CHANGELOG.md to v2.5.0 with Red Panda JPL ([bf44e9f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/bf44e9f))
* Fix docker-compose issue ([46d7269](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/46d7269))
* Run test using docker compose, instead of standalone docker ([9eb20b9](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9eb20b9))

### Docs

* Update README.md ([051102a](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/051102a))

## v2.4.0 (2017-12-28)

### New

* Set fastlane trigers asociated with fastlane/* branches on android/iOS buidls (fixes #49) (#57) ([e76d9ba](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e76d9ba))
* Add iOS build capabilities (fixes #44) (#55) ([3dab390](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/3dab390))

### Update

* Add target platform to jpl-git-cache project folder ([42e0be5](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/42e0be5))

### Build

* Update CHANGELOG.md to v2.4.0 with Red Panda JPL ([2f43d1e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/2f43d1e))

## v2.3.5 (2017-12-27)

### Update

* Do a second checkout try wiping directory if the first try fails ([138a5d4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/138a5d4))

### Fix

* Change cache folder in jpl-git-cache (fixes #53) (#54) ([b6c8cb9](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b6c8cb9))

### Build

* Update CHANGELOG.md to v2.3.5 with Red Panda JPL ([1efe0c6](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1efe0c6))

## v2.3.4 (2017-12-26)

### New

* Add jpl git cache capability (fixes #50) (#51) ([d0df6bd](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d0df6bd))

### Update

* Add config test to all jpl helpers ([78164fe](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/78164fe))
* Refactor test ([bcb5dd6](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/bcb5dd6))

### Build

* Update CHANGELOG.md to v2.3.4 with Red Panda JPL ([09df88b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/09df88b))

### Docs

* Update readme ([2cdc43b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/2cdc43b))

## v2.3.3 (2017-12-13)

### New

* Add jplDockerBuild helper, with tests ([eece8cc](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/eece8cc))

### Build

* Update CHANGELOG.md to v2.3.3 with Red Panda JPL ([87a8480](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/87a8480))
* Use last stable jpl release ([d2ed871](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d2ed871))

## v2.3.2 (2017-12-12)

### Fix

* Use the right jplCheckoutSCM helper within jplPromoteCode ([6b2af9a](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6b2af9a))

### Build

* Update CHANGELOG.md to v2.3.2 with Red Panda JPL ([8da7761](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/8da7761))

## v2.3.1 (2017-12-12)

### Fix

* Use master branch of jpl-scripts library ([59e5797](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/59e5797))
* Use jpl-scripts istead ci-scripts library ([3bbb558](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/3bbb558))

### Build

* Update CHANGELOG.md to v2.3.1 with Red Panda JPL ([8301d32](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/8301d32))

## v2.3.0 (2017-12-10)

### Update

* Use the right android docker image ([865a2b4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/865a2b4))
* Use latest docker image of redpandaci/jpl-android-base (#41) ([64d37b3](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/64d37b3))
* Refactor for release v2.3.0 (#40) ([696c797](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/696c797))

### Build

* Update CHANGELOG.md to v2.3.0 with Red Panda JPL ([0eeebcf](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0eeebcf))

## v2.2.2 (2017-12-04)

### Update

* Change jplBuildAPK workflow ([e6c530b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e6c530b))
* Add old config to Docker android base ([e129898](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e129898))

### Build

* Update CHANGELOG.md to v2.2.2 with Red Panda JPL ([428a5c5](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/428a5c5))

## v2.2.1 (2017-12-03)

### Update

* Don't use '?' folder in apk builds (#37) ([12ff8bd](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/12ff8bd))

### Build

* Update CHANGELOG.md to v2.2.1 with Red Panda JPL ([388ddb7](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/388ddb7))
* Remove Travis CI integration ([e87a789](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e87a789))

### Docs

* Update README.md and helper docs ([336914a](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/336914a))

## v2.2.0 (2017-12-03)

### New

* Build APK with Jenkins Pipeline docker way (fixes #5) (#36) ([7f2d36d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/7f2d36d))

### Update

* Revert checkout behauviour ([401ba22](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/401ba22))
* Avoid to do a pull in jplCheckoutSCM ([0735405](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0735405))
* Align commit messages on git tasks ([8c77588](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/8c77588))

### Build

* Update CHANGELOG.md to v2.2.0 with Red Panda JPL ([efeee5d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/efeee5d))

## v2.1.1 (2017-11-14)

### New

* Add new cfg.releaseTagNumber key ([ed30682](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/ed30682))
* Add new config.releaseTag key ([8d95a98](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/8d95a98))

### Update

* Change jplCloseRelease test adding cfg.releaseTag info ([b2f9de0](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b2f9de0))
* Remove deprecated info in jplSigning helper ([886a77f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/886a77f))

### Build

* Update CHANGELOG.md to v2.1.1 with Red Panda JPL ([8805a8e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/8805a8e))
* Remove travis integration and related build status badge ([438eb9a](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/438eb9a))
* Update version number in Sonar properties file ([b36e0c4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b36e0c4))

### Docs

* Update README.md ([cab6156](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/cab6156))
* Fix doc typo in CONTRIBUTING.md ([67fde91](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/67fde91))
* Add pipeline documentation in vars/*.txt files ([aa3634b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/aa3634b))

## v2.1.0 (2017-11-11)

### Breaking

* Add dockerTag parameter to jplDockerPush ([478b694](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/478b694))

### New

* Add firstTag info to changelog build ([afa380b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/afa380b))

### Fix

* Set the right URL pointing to commits in jplBuildChangelog ([d0fa68f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d0fa68f))

### Build

* Update CHANGELOG.md to v2.1.0 with Red Panda JPL ([bb62b87](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/bb62b87))
* Set firstTag for changelog build ([0b5d5c6](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0b5d5c6))

### Docs

* Improve documentation (fixes #34) ([55e80ae](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/55e80ae))

## v2.0.0 (2017-11-05)

### Breaking

* Refactor jplCheckoutSCM helper (fixes #31) ([cf62e8f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/cf62e8f))

### New

* Add jplBuildChangelog (refs #23) ([9d63050](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9d63050))
* Add jplStart helper with test (refs #31) ([6791dc4](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6791dc4))

### Update

* Add promote build timeout (fixes #22) ([7aadd53](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/7aadd53))
* Add CHANGELOG.md file in jplCloseRelease (fixes #33) ([1454771](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1454771))
* Adapt jplPromoteBuildTest (refs #31) ([ed1a949](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/ed1a949))
* Execute jplStartTest (refs #23) ([0b2a64c](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0b2a64c))
* Remove credential helper configuration in jplPromoteCode and jplCloseRelease helpers ([13cd3ba](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/13cd3ba))

### Build

* Update CHANGELOG.md to v2.0.0 with Red Panda JPL ([445a34d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/445a34d))
* Update to new v2.0.0 jpl development pipeline workflow ([e22a803](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e22a803))
* Set release number to v2.0.0 in sonar ([38eae2b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/38eae2b))
* Use develop jpl ([f837221](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/f837221))
* Use jpl v1.4.2 ([d05f972](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d05f972))

### Docs

* Update README.md and helpers documentation ([20863e9](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/20863e9))

## v1.4.2 (2017-11-01)

### Breaking

* Move from oracle to openjdk jdk, some changes in the build process ([2031184](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/2031184))

### New

* Add test for jplCloseRelease helper ([d6569fe](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d6569fe))
* Add jplPromoteBuild test ([021d465](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/021d465))
* Add jplDocker test ([174f4e5](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/174f4e5))
* Add jplDocker helper ([b6d0627](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b6d0627))
* Add timeout docker container for tests ([c8113ae](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/c8113ae))

### Update

* Add branch config to jplPromoteCode (refs #23) ([bea390e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/bea390e))
* Change jplPromoteBuild name to jplPromoteBuildTest ([d871445](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d871445))
* Change jplCheckoutSCM test name to jplCheckoutSCMTest ([9323c3b](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9323c3b))
* Add branch config to jplCloseRelease (refs #23) ([6b2fc35](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6b2fc35))
* Add branch config to jplCloseRelease (refs #23) ([8ddbcd7](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/8ddbcd7))
* Abort build with "ABORT" build result in jplSonarScanner if the quality gate fails ([36993a7](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/36993a7))
* Tests refactoring ([7a3e6d3](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/7a3e6d3))
* Rename helper jplDocker => jplDockerPush ([87898b6](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/87898b6))
* Add stage to the jplCheckoutSCM test ([e16291d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e16291d))
* Review jplCheckoutSCM test ([1a733d2](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1a733d2))
* Improve parameters in jplPromoteBuild ([2b1a293](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/2b1a293))
* Use github repository in jplCheckoutSCM test ([f1166a1](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/f1166a1))
* Remove all 'timestamp' and 'ansicolor' references ([4db6eb8](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/4db6eb8))

### Fix

* Change checkout branches order for the tests ([013f2e0](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/013f2e0))
* Resolve bug with return value management in bin/test.sh ([518e1f5](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/518e1f5))
* Change 'fake' branch creation for testing in bin/test.sh ([cfc5622](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/cfc5622))
* Set git 'store' credential.helper option in jplPromoteCode helper ([9553498](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9553498))
* Set git 'store' credential.helper option in jplCloseRelease helper ([9d62ef7](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9d62ef7))
* Add git configuration within docker for testing ([7a018c0](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/7a018c0))
* Resolve jplCloseReleaseTest issue with develop/master branches ([c938614](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/c938614))
* Add branch config to jplPromoteCode (fixes #23) ([444949f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/444949f))
* Resolve Sonar bug with variable usage in jplDocker helper ([4683e4a](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/4683e4a))
* Remove test stuff from bin/test.sh ([37690af](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/37690af))
* Corrected jplDocker test ([951a554](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/951a554))
* Clean ci-scripts stuff ([b49d230](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b49d230))
* Resolve Sonar bugs and codesmells ([256421f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/256421f))

### Build

* Execute test run also with release branches ([975d710](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/975d710))
* Refactor test ([bf8e35c](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/bf8e35c))
* Prevent PR from test failures ([61086bd](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/61086bd))
* Fix badge of Jenkins build ([1c28137](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1c28137))
* Fix build badge ([d7b85bb](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d7b85bb))
* Change Jenkins badge ([933512c](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/933512c))
* Archive test result logs as build artifacts ([1bc560d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1bc560d))
* Add redpanda slack integration ([ddeea59](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/ddeea59))
* Recover Quality Gate bugs ([e80f6fd](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/e80f6fd))
* Use develop library for test and build ([ccc217d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/ccc217d))
* Add timestamps and ansiColor to pipeline options ([74aadf1](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/74aadf1))
* Enable SonarQube scan ([b98adec](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/b98adec))
* Remove test container after test process ([66d2e6c](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/66d2e6c))

### Docs

* Update main readme ([615d042](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/615d042))
* Update main readme ([75997ed](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/75997ed))
* Improve README.md adding testing instructions ([1bcef0d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1bcef0d))
* Update README.md ([3783283](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/3783283))

## v1.4.1 (2017-09-21)

### New

* Include Jenkinsfile ([f00c380](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/f00c380))

### Update

* Remove unnecesary projectName parameter in jplSigning ([078c52a](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/078c52a))
* Use signApk.sh from ci-scripts repository ([348d56d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/348d56d))
* Exec android stuff on docker container for jplSigning ([bbe356f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/bbe356f))
* Align APK's after signing (fixes #15) ([0eacd71](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0eacd71))

### Fix

* Use the right signApk.sh parameter value ([c273458](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/c273458))
* Some jplSigning typos ([0db7311](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0db7311))

### Build

* Fixes Jenkinsfile typo ([9d441d3](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9d441d3))

## v1.4.0 (2017-09-19)

### New

* Add first test using jenkins-dind ([1777435](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1777435))

### Update

* Add docker function prefix cfg item ([fd87241](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/fd87241))
* Delete generated changelog report file (Closes #11) ([28c642e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/28c642e))
* Delete generated changelog report file (Closes #7) ([10647fb](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/10647fb))

### Fix

* Remove changelog report file after publish html ([1ea5691](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/1ea5691))
* Use correct fileOperations syntax ([185f6fe](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/185f6fe))
* Stop container instead forcing the remove (Closes #10) ([0337f60](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0337f60))

## v1.3.0 (2017-09-15)

### New

* Attach changelog report to the build using Jenkins HTML Publish Plugin in checkout step ([68e3f6d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/68e3f6d))

### Update

* Change all repository dependencies to red-panda-ci ([472b000](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/472b000))
* Alignd with latest commit validator container interface ([d25dd43](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/d25dd43))
* Don't keep all changelog html reports ([3ae2af3](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/3ae2af3))
* Set correct changelog generation condition ([5a1fdff](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/5a1fdff))
* Use new 'FORMAT' parameter in changelog report, output as html ([09753cb](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/09753cb))

### Fix

* Brackets typo ([18e562c](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/18e562c))
* Build the changelog report only once ([a54befe](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/a54befe))

## v1.2.0 (2017-09-13)

### New

* Add changelog generator on repository checkout ([ded9539](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/ded9539))

### Update

* Change jplValidateCommit function container usage ([9ec0c10](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/9ec0c10))

### Fix

* Avoid use of merges in commit messages recovery of jplValidateCommitMessages ([6ab4d96](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/6ab4d96))

## v1.1.0 (2017-09-12)

### New

* Run commit validator as a docker function ([c5e90f5](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/c5e90f5))
* jplValidateCommitMessages functionality ([2885a3d](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/2885a3d))

### Update

* Don't break the build when the result of sonar is a warning ([5d4eb3e](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/5d4eb3e))
* Convert commit delimiter to '
' string ([4173288](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/4173288))

### Fix

* Typo ([03bf598](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/03bf598))
* isNull function groovy doesn't exist ([0da2f9f](https://github.com:red-panda-ci/jenkins-pipeline-library/commit/0da2f9f))

