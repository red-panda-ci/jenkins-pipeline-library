<a name="2.3.3"></a>
## 2.3.3 (2017-12-13)


### Build

* Use last stable jpl release ([d2ed8719ea5072bf98b53449d3ff3d62d8480282](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/d2ed8719ea5072bf98b53449d3ff3d62d8480282))

### New

* Add jplDockerBuild helper, with tests ([eece8ccd6c305242b4026cd5efddd2a0579e65c5](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/eece8ccd6c305242b4026cd5efddd2a0579e65c5))



<a name="2.3.2"></a>
## 2.3.2 (2017-12-12)


### Build

* Update CHANGELOG.md to v2.3.2 with Red Panda JPL ([8da776199c35c70a464f61d79830ebe6de0a0237](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/8da776199c35c70a464f61d79830ebe6de0a0237))

### Fix

* Use the right jplCheckoutSCM helper within jplPromoteCode ([6b2af9a1f5d0b976bb1ccabecdb663816c4a904f](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/6b2af9a1f5d0b976bb1ccabecdb663816c4a904f))



<a name="2.3.1"></a>
## 2.3.1 (2017-12-12)


### Build

* Update CHANGELOG.md to v2.3.1 with Red Panda JPL ([8301d3283681f83c3b2e40826a4fcf0a634dd905](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/8301d3283681f83c3b2e40826a4fcf0a634dd905))

### Fix

* Use jpl-scripts istead ci-scripts library ([3bbb558ff39525afe995ca44c41709b360f22468](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/3bbb558ff39525afe995ca44c41709b360f22468))
* Use master branch of jpl-scripts library ([59e5797e46149222771f9ea01d0bafdf5bda88d5](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/59e5797e46149222771f9ea01d0bafdf5bda88d5))



<a name="2.3.0"></a>
# 2.3.0 (2017-12-10)


### Build

* Update CHANGELOG.md to v2.3.0 with Red Panda JPL ([0eeebcfccfe066a7e775dec35d97437cd85116cd](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/0eeebcfccfe066a7e775dec35d97437cd85116cd))

### Update

* Refactor for release v2.3.0  ([696c7970e48b9cd8bc7c4b0039afc220495c674a](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/696c7970e48b9cd8bc7c4b0039afc220495c674a))
* Use latest docker image of redpandaci/jpl-android-base  ([64d37b399c973ed4cdce63c319291cdbcbb5faf1](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/64d37b399c973ed4cdce63c319291cdbcbb5faf1))
* Use the right android docker image ([865a2b4cea30128e5b69cd7684ec8b5961d47424](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/865a2b4cea30128e5b69cd7684ec8b5961d47424))



<a name="2.2.2"></a>
## 2.2.2 (2017-12-04)


### Build

* Update CHANGELOG.md to v2.2.2 with Red Panda JPL ([428a5c54a6677da1c26c867d80a8d365f1114b45](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/428a5c54a6677da1c26c867d80a8d365f1114b45))

### Update

* Add old config to Docker android base ([e129898fa81b3335f639f155f6fafe322ab5a90b](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/e129898fa81b3335f639f155f6fafe322ab5a90b))
* Change jplBuildAPK workflow ([e6c530b66ca024e40379079a221bfa498f095ef6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/e6c530b66ca024e40379079a221bfa498f095ef6))



<a name="2.2.1"></a>
## 2.2.1 (2017-12-03)


### Build

* Remove Travis CI integration ([e87a789f950aa78c21f433b2a3911e1b2016786c](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/e87a789f950aa78c21f433b2a3911e1b2016786c))
* Update CHANGELOG.md to v2.2.1 with Red Panda JPL ([388ddb7636ce05bb918bfe8d73e617b491a2e591](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/388ddb7636ce05bb918bfe8d73e617b491a2e591))

### Docs

* Update README.md and helper docs ([336914ae190a3b88302e0cb336a51ef3626c9d28](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/336914ae190a3b88302e0cb336a51ef3626c9d28))



<a name="2.2.0"></a>
# 2.2.0 (2017-12-03)


### Build

* Update CHANGELOG.md to v2.2.0 with Red Panda JPL ([efeee5d81f0ae07dbf77c9bd7c4463cc2dc0b6d0](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/efeee5d81f0ae07dbf77c9bd7c4463cc2dc0b6d0))

### New

* Build APK with Jenkins Pipeline docker way  ([7f2d36d1ac5c665c799aef78339c360aff8896f5](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/7f2d36d1ac5c665c799aef78339c360aff8896f5)), closes [#5](https://github.com/madoos/node-changelog-generator/issues/5) [#36](https://github.com/madoos/node-changelog-generator/issues/36)

### Update

* Align commit messages on git tasks ([8c77588c9849ee4c019022f03945c9752cd10881](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/8c77588c9849ee4c019022f03945c9752cd10881))
* Avoid to do a pull in jplCheckoutSCM ([073540541071f326c4908f7b104c6bd1b0835817](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/073540541071f326c4908f7b104c6bd1b0835817))
* Revert checkout behauviour ([401ba224db581b615f4f0b19f370bbb20484b3e4](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/401ba224db581b615f4f0b19f370bbb20484b3e4))



<a name="2.1.1"></a>
## 2.1.1 (2017-11-14)


### Build

* Remove travis integration and related build status badge ([438eb9aa81c5259882e738b320257ebb00ee5533](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/438eb9aa81c5259882e738b320257ebb00ee5533))
* Update CHANGELOG.md to v2.1.1 with Red Panda JPL ([8805a8e932d414b1efaceabae47046611efca23d](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/8805a8e932d414b1efaceabae47046611efca23d))
* Update version number in Sonar properties file ([b36e0c4216c300ba8fdca63a87cee75a9a078df6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/b36e0c4216c300ba8fdca63a87cee75a9a078df6))

### Docs

* Add pipeline documentation in vars/*.txt files ([aa3634beae90a34d4f4dc8e5b6e6e3e9a1d77f65](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/aa3634beae90a34d4f4dc8e5b6e6e3e9a1d77f65))
* Fix doc typo in CONTRIBUTING.md ([67fde91fc53bf0a92baaa64d070ba6d8af34134b](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/67fde91fc53bf0a92baaa64d070ba6d8af34134b))
* Update README.md ([cab61561451bb7ab1764f7f5fcfe8131f7249523](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/cab61561451bb7ab1764f7f5fcfe8131f7249523))

### New

* Add new cfg.releaseTagNumber key ([ed30682ac4086e4ec79ade9f03f6ab85828262db](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/ed30682ac4086e4ec79ade9f03f6ab85828262db))
* Add new config.releaseTag key ([8d95a98b29856a80d777638161799fbccff8893d](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/8d95a98b29856a80d777638161799fbccff8893d))

### Update

* Change jplCloseRelease test adding cfg.releaseTag info ([b2f9de060ff311f25908e00c0cafc257a3c6ff03](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/b2f9de060ff311f25908e00c0cafc257a3c6ff03))
* Remove deprecated info in jplSigning helper ([886a77fc6c90bf79396bad808f610a9d93c7d240](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/886a77fc6c90bf79396bad808f610a9d93c7d240))



<a name="2.1.0"></a>
# 2.1.0 (2017-11-11)


### Breaking

* Add dockerTag parameter to jplDockerPush ([478b694bf64a123fcb7a585210d72396f6bd5bf5](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/478b694bf64a123fcb7a585210d72396f6bd5bf5))

### Build

* Set firstTag for changelog build ([0b5d5c6bb6b4b6e463217bcfd386b915bdddc151](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/0b5d5c6bb6b4b6e463217bcfd386b915bdddc151))
* Update CHANGELOG.md to v2.1.0 with Red Panda JPL ([bb62b872529114875809f5a80ebcd7681c3c168e](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/bb62b872529114875809f5a80ebcd7681c3c168e))

### Docs

* Improve documentation  ([55e80aefebec44046b1eace7b60bde7fd6ca4086](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/55e80aefebec44046b1eace7b60bde7fd6ca4086)), closes [#34](https://github.com/madoos/node-changelog-generator/issues/34)

### Fix

* Set the right URL pointing to commits in jplBuildChangelog ([d0fa68f65ea8de0bf69bba040bcf8f7bdacc6426](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/d0fa68f65ea8de0bf69bba040bcf8f7bdacc6426))

### New

* Add firstTag info to changelog build ([afa380b8c757d12e9e90c5a852618ed6644543b6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/afa380b8c757d12e9e90c5a852618ed6644543b6))



<a name="2.0.0"></a>
# 2.0.0 (2017-11-05)


### Breaking

* Refactor jplCheckoutSCM helper  ([cf62e8f9ad9315c248ad74e34dc2bf68bf744bb2](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/cf62e8f9ad9315c248ad74e34dc2bf68bf744bb2)), closes [#31](https://github.com/madoos/node-changelog-generator/issues/31)

### Build

* Set release number to v2.0.0 in sonar ([38eae2bb687058fac7feb1f7658443cb5764c0fc](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/38eae2bb687058fac7feb1f7658443cb5764c0fc))
* Update CHANGELOG.md to v2.0.0 with Red Panda JPL ([445a34d73b8e6e6c72eb8f400ffd448aa1337434](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/445a34d73b8e6e6c72eb8f400ffd448aa1337434))
* Update to new v2.0.0 jpl development pipeline workflow ([e22a803257022b89b6470294696611efb3d3ec62](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/e22a803257022b89b6470294696611efb3d3ec62))
* Use develop jpl ([f8372212dcff5407747bbf672ce5c599bf1f4ba4](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/f8372212dcff5407747bbf672ce5c599bf1f4ba4))
* Use jpl v1.4.2 ([d05f97251ae58502c8eea6f8647cccd53ef8078d](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/d05f97251ae58502c8eea6f8647cccd53ef8078d))

### Docs

* Update README.md and helpers documentation ([20863e9d275b92bd3ccf7d026de33882e4455f0c](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/20863e9d275b92bd3ccf7d026de33882e4455f0c))

### New

* Add jplBuildChangelog  ([9d630502119cbad79147fdb10555198f1a67f50c](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/9d630502119cbad79147fdb10555198f1a67f50c))
* Add jplStart helper with test  ([6791dc408030031f526a0d2964ec03d4aef628c9](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/6791dc408030031f526a0d2964ec03d4aef628c9))

### Update

* Adapt jplPromoteBuildTest  ([ed1a949a021675664d1e221deb9d614056116142](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/ed1a949a021675664d1e221deb9d614056116142))
* Add CHANGELOG.md file in jplCloseRelease  ([145477117630e7326c0fb988775711d87e9c3b87](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/145477117630e7326c0fb988775711d87e9c3b87)), closes [#33](https://github.com/madoos/node-changelog-generator/issues/33)
* Add promote build timeout  ([7aadd53fe62da33735d4ee5f20089d5c95066884](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/7aadd53fe62da33735d4ee5f20089d5c95066884)), closes [#22](https://github.com/madoos/node-changelog-generator/issues/22)
* Execute jplStartTest  ([0b2a64c942f83e4bdbb6087875a1d19a3cf491ab](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/0b2a64c942f83e4bdbb6087875a1d19a3cf491ab))
* Remove credential helper configuration in jplPromoteCode and jplCloseRelease helpers ([13cd3baa945031aa803715bfe14ef70eead1051a](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/13cd3baa945031aa803715bfe14ef70eead1051a))



<a name="1.4.2"></a>
## 1.4.2 (2017-11-01)


### Breaking

* Move from oracle to openjdk jdk, some changes in the build process ([2031184a88a2248027a605822cab85ec84cf4564](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/2031184a88a2248027a605822cab85ec84cf4564))

### Build

* Add redpanda slack integration ([ddeea597de482c5b6f290ac1ed4ac686d9fd24f8](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/ddeea597de482c5b6f290ac1ed4ac686d9fd24f8))
* Add timestamps and ansiColor to pipeline options ([74aadf15c5c3c52314630eb5a945567fb7fca99e](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/74aadf15c5c3c52314630eb5a945567fb7fca99e))
* Archive test result logs as build artifacts ([1bc560d96b23c26f010f8fec98a726df0fe2374a](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/1bc560d96b23c26f010f8fec98a726df0fe2374a))
* Change Jenkins badge ([933512cdc1e2fb51aa748cee844dbb0c6f21c041](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/933512cdc1e2fb51aa748cee844dbb0c6f21c041))
* Enable SonarQube scan ([b98adec2e782fe2f4fc3be566cd30864a3dd2db0](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/b98adec2e782fe2f4fc3be566cd30864a3dd2db0))
* Execute test run also with release branches ([975d7100797b6f3f516f1ab7968e87f481a14eed](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/975d7100797b6f3f516f1ab7968e87f481a14eed))
* Fix badge of Jenkins build ([1c28137f2edb5410ad584fe549518471d6da3c9a](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/1c28137f2edb5410ad584fe549518471d6da3c9a))
* Fix build badge ([d7b85bbfc059e99f3621da759e08e605f663bdb6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/d7b85bbfc059e99f3621da759e08e605f663bdb6))
* Prevent PR from test failures ([61086bdcf084094cd9e81e0cbb266f9a7309cb09](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/61086bdcf084094cd9e81e0cbb266f9a7309cb09))
* Recover Quality Gate bugs ([e80f6fd2833451c6deaa392d245f23093993c32f](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/e80f6fd2833451c6deaa392d245f23093993c32f))
* Refactor test ([bf8e35cf3a5b008855e837187cefed56ee294b18](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/bf8e35cf3a5b008855e837187cefed56ee294b18))
* Remove test container after test process ([66d2e6c037dfddf4cd9395c3e1940abcf80039a4](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/66d2e6c037dfddf4cd9395c3e1940abcf80039a4))
* Use develop library for test and build ([ccc217d12e1e692d0e8224aef2f594c6f473817c](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/ccc217d12e1e692d0e8224aef2f594c6f473817c))

### Docs

* Improve README.md adding testing instructions ([1bcef0dd1bee031a1a2a89dd2faa25aaed1b29da](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/1bcef0dd1bee031a1a2a89dd2faa25aaed1b29da))
* Update main readme ([615d0424d271ddea44b7b99c53963e0a36c2c18b](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/615d0424d271ddea44b7b99c53963e0a36c2c18b))
* Update main readme ([75997edf0105192fa41606bd72c77f48841c98a9](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/75997edf0105192fa41606bd72c77f48841c98a9))
* Update README.md ([37832834a3da3deec01f74845f33ba03edb5d605](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/37832834a3da3deec01f74845f33ba03edb5d605))

### Fix

* Add branch config to jplPromoteCode  ([444949f55104e33c95053b2bb7f664e6e153c5be](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/444949f55104e33c95053b2bb7f664e6e153c5be)), closes [#23](https://github.com/madoos/node-changelog-generator/issues/23)
* Add git configuration within docker for testing ([7a018c0e02a67ccfa843b4ab980f4a2fae545177](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/7a018c0e02a67ccfa843b4ab980f4a2fae545177))
* Change 'fake' branch creation for testing in bin/test.sh ([cfc5622c2c04992b3974dec5ce165a0cabd44e72](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/cfc5622c2c04992b3974dec5ce165a0cabd44e72))
* Change checkout branches order for the tests ([013f2e0aca4c4de1ce82e77cb32418dc29e6690b](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/013f2e0aca4c4de1ce82e77cb32418dc29e6690b))
* Clean ci-scripts stuff ([b49d230d330de0cb8ecd82d247c1a62186ec4659](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/b49d230d330de0cb8ecd82d247c1a62186ec4659))
* Corrected jplDocker test ([951a5545f704e9dc904c144fa0a29698c08ac551](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/951a5545f704e9dc904c144fa0a29698c08ac551))
* Remove test stuff from bin/test.sh ([37690af9915cdefe7b233ca19cd4dc742bb78b7b](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/37690af9915cdefe7b233ca19cd4dc742bb78b7b))
* Resolve bug with return value management in bin/test.sh ([518e1f5d1ed4ac660ab5ca1e5aa0c60c96f00602](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/518e1f5d1ed4ac660ab5ca1e5aa0c60c96f00602))
* Resolve jplCloseReleaseTest issue with develop/master branches ([c9386141e3063ace9ce10206ee78de4d0a728617](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/c9386141e3063ace9ce10206ee78de4d0a728617))
* Resolve Sonar bug with variable usage in jplDocker helper ([4683e4a561431bd18749b3d94faca92256ac3d36](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/4683e4a561431bd18749b3d94faca92256ac3d36))
* Resolve Sonar bugs and codesmells ([256421f68bdd73e5823f7f7abae55e53e9094bf5](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/256421f68bdd73e5823f7f7abae55e53e9094bf5))
* Set git 'store' credential.helper option in jplCloseRelease helper ([9d62ef7d86ec6a14e718b222ab0d2a2a042ba2d1](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/9d62ef7d86ec6a14e718b222ab0d2a2a042ba2d1))
* Set git 'store' credential.helper option in jplPromoteCode helper ([9553498228ac1843fbe82c268e7be57d50d4d2e9](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/9553498228ac1843fbe82c268e7be57d50d4d2e9))

### New

* Add jplDocker helper ([b6d0627804e9bf71b5e522172db51043e958d8a2](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/b6d0627804e9bf71b5e522172db51043e958d8a2))
* Add jplDocker test ([174f4e56c23a3a922aee4ddb698f5b1940fec771](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/174f4e56c23a3a922aee4ddb698f5b1940fec771))
* Add jplPromoteBuild test ([021d46580257dd71fa2a0d91a8947d66cca62155](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/021d46580257dd71fa2a0d91a8947d66cca62155))
* Add test for jplCloseRelease helper ([d6569fe41f2783e766397b95424f345372ae2d94](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/d6569fe41f2783e766397b95424f345372ae2d94))
* Add timeout docker container for tests ([c8113ae377740b99cb8116876d1071418afa91c3](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/c8113ae377740b99cb8116876d1071418afa91c3))

### Update

* Abort build with "ABORT" build result in jplSonarScanner if the quality gate fails ([36993a7cd8dc1a289ec05309d8b0645187651570](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/36993a7cd8dc1a289ec05309d8b0645187651570))
* Add branch config to jplCloseRelease  ([6b2fc359442b31f2a16c5c2f033af0bec54ad26e](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/6b2fc359442b31f2a16c5c2f033af0bec54ad26e))
* Add branch config to jplCloseRelease  ([8ddbcd725de4ae75c57aed172b7134a091c2a8af](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/8ddbcd725de4ae75c57aed172b7134a091c2a8af))
* Add branch config to jplPromoteCode  ([bea390e1e63981f43dab380f3cdd5b49f67f7a1a](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/bea390e1e63981f43dab380f3cdd5b49f67f7a1a))
* Add stage to the jplCheckoutSCM test ([e16291d4ee21b881677f09b8e59b7addb099e39f](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/e16291d4ee21b881677f09b8e59b7addb099e39f))
* Change jplCheckoutSCM test name to jplCheckoutSCMTest ([9323c3b495fbd177a7a681b187bc798137d2a588](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/9323c3b495fbd177a7a681b187bc798137d2a588))
* Change jplPromoteBuild name to jplPromoteBuildTest ([d87144549ca74190d16eed739489ccedc029f7ac](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/d87144549ca74190d16eed739489ccedc029f7ac))
* Improve parameters in jplPromoteBuild ([2b1a2937a0809580be72cbc535841258d2d60201](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/2b1a2937a0809580be72cbc535841258d2d60201))
* Remove all 'timestamp' and 'ansicolor' references ([4db6eb87b6ac7a654568ea8033ea3182154d99b8](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/4db6eb87b6ac7a654568ea8033ea3182154d99b8))
* Rename helper jplDocker => jplDockerPush ([87898b69635ae4030268e492286a796b439831e4](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/87898b69635ae4030268e492286a796b439831e4))
* Review jplCheckoutSCM test ([1a733d2b4e0f9d7de35c63c8f31b04cd88be8e32](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/1a733d2b4e0f9d7de35c63c8f31b04cd88be8e32))
* Tests refactoring ([7a3e6d3c199db593a7a44eda3feff3b5093ea8bf](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/7a3e6d3c199db593a7a44eda3feff3b5093ea8bf))
* Use github repository in jplCheckoutSCM test ([f1166a1700b5f50fcfe529494c232d46afce0fc5](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/f1166a1700b5f50fcfe529494c232d46afce0fc5))



<a name="1.4.1"></a>
## 1.4.1 (2017-09-21)


### Build

* Fixes Jenkinsfile typo ([9d441d3ca5a77c44337bde3e0ba5f05c5620e519](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/9d441d3ca5a77c44337bde3e0ba5f05c5620e519))

### Fix

* Some jplSigning typos ([0db7311e803638394ba9f3c4e32a1791f7dae0c6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/0db7311e803638394ba9f3c4e32a1791f7dae0c6))
* Use the right signApk.sh parameter value ([c27345831281834ee537226faf453aa156ae8cc0](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/c27345831281834ee537226faf453aa156ae8cc0))

### New

* Include Jenkinsfile ([f00c380d99c0c04cd94b72c0b2320e11538faa6d](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/f00c380d99c0c04cd94b72c0b2320e11538faa6d))

### Update

* Align APK's after signing  ([0eacd71f134234c14678b9b10f0fb74bb2546506](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/0eacd71f134234c14678b9b10f0fb74bb2546506)), closes [#15](https://github.com/madoos/node-changelog-generator/issues/15)
* Exec android stuff on docker container for jplSigning ([bbe356fd6096d5a8bd8221818f7598b4ccf9a3a3](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/bbe356fd6096d5a8bd8221818f7598b4ccf9a3a3))
* Remove unnecesary projectName parameter in jplSigning ([078c52a70e22208b7136c7d30a03854667cdacb9](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/078c52a70e22208b7136c7d30a03854667cdacb9))
* Use signApk.sh from ci-scripts repository ([348d56d130f3510524efe22e489577c45a7eb08c](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/348d56d130f3510524efe22e489577c45a7eb08c))



<a name="1.4.0"></a>
# 1.4.0 (2017-09-19)


### Fix

* Remove changelog report file after publish html ([1ea56918afda076b84ea6915c2a333d021c8d611](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/1ea56918afda076b84ea6915c2a333d021c8d611))
* Stop container instead forcing the remove  ([0337f6095c392cb28c2c6407d55171e621615af7](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/0337f6095c392cb28c2c6407d55171e621615af7)), closes [#10](https://github.com/madoos/node-changelog-generator/issues/10)
* Use correct fileOperations syntax ([185f6fef72b75016395113124a85ae13947f76a6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/185f6fef72b75016395113124a85ae13947f76a6))

### New

* Add first test using jenkins-dind ([17774351d45652b67ba64b3c2a864c2ab477d006](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/17774351d45652b67ba64b3c2a864c2ab477d006))

### Update

* Add docker function prefix cfg item ([fd8724176bc2fe874d505a4b7dc360903e317f84](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/fd8724176bc2fe874d505a4b7dc360903e317f84))
* Delete generated changelog report file  ([28c642e5f71f5b7213058394cec11b68ec75c5bf](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/28c642e5f71f5b7213058394cec11b68ec75c5bf)), closes [#11](https://github.com/madoos/node-changelog-generator/issues/11)
* Delete generated changelog report file  ([10647fb722b9c318091cb002a06507ed393625cc](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/10647fb722b9c318091cb002a06507ed393625cc)), closes [#7](https://github.com/madoos/node-changelog-generator/issues/7)



<a name="1.3.0"></a>
# 1.3.0 (2017-09-15)


### Fix

* Avoid use of merges in commit messages recovery of jplValidateCommitMessages ([6ab4d962ff9ac71fa0427d770f55013d825641c9](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/6ab4d962ff9ac71fa0427d770f55013d825641c9))
* Brackets typo ([18e562ce02c4478b0b174b70b4e2c0ffad3feda8](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/18e562ce02c4478b0b174b70b4e2c0ffad3feda8))
* Build the changelog report only once ([a54befecd3d72750de568d5ccc22634b82267335](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/a54befecd3d72750de568d5ccc22634b82267335))
* isNull function groovy doesn't exist ([0da2f9f06c36a57281a23e0f221285e27d86a636](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/0da2f9f06c36a57281a23e0f221285e27d86a636))
* Typo ([03bf598107409bbca434df4fd966ee720716d775](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/03bf598107409bbca434df4fd966ee720716d775))

### New

* Add changelog generator on repository checkout ([ded953919e9f811ae506e76903711b5e05179192](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/ded953919e9f811ae506e76903711b5e05179192))
* Attach changelog report to the build using Jenkins HTML Publish Plugin in checkout step ([68e3f6db197d44b6652d1031e19d5924631c556c](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/68e3f6db197d44b6652d1031e19d5924631c556c))
* jplValidateCommitMessages functionality ([2885a3d2c2b5fb65c35ba998c604e88664e87ce8](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/2885a3d2c2b5fb65c35ba998c604e88664e87ce8))
* Run commit validator as a docker function ([c5e90f5fd6a37e726af2454eaec8bc722c35e9cd](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/c5e90f5fd6a37e726af2454eaec8bc722c35e9cd))

### Update

* Alignd with latest commit validator container interface ([d25dd43ba833fae1502d24ac2f1da00b1ef90577](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/d25dd43ba833fae1502d24ac2f1da00b1ef90577))
* Change all repository dependencies to red-panda-ci ([472b000ef81c9b17d527e645a1252d037d892b80](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/472b000ef81c9b17d527e645a1252d037d892b80))
* Change jplValidateCommit function container usage ([9ec0c104742a53ac6f50004182bada7aec09c7e6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/9ec0c104742a53ac6f50004182bada7aec09c7e6))
* Convert commit delimiter to '\n' string ([417328846c290ecd8e6fec546f2785560b4f85b7](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/417328846c290ecd8e6fec546f2785560b4f85b7))
* Don't break the build when the result of sonar is a warning ([5d4eb3e2a0ad63174833b59c5915bead3720fcd6](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/5d4eb3e2a0ad63174833b59c5915bead3720fcd6))
* Don't keep all changelog html reports ([3ae2af3567c0e40dcc78f0594ebbe05351848743](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/3ae2af3567c0e40dcc78f0594ebbe05351848743))
* Set correct changelog generation condition ([5a1fdff75d45fa1c3ddc02538a53e74d79222825](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/5a1fdff75d45fa1c3ddc02538a53e74d79222825))
* Use new 'FORMAT' parameter in changelog report, output as html ([09753cbcbc06a18e584d77ae506cbe3498534399](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/09753cbcbc06a18e584d77ae506cbe3498534399))



<a name="0.8.0"></a>
# 0.8.0 (2017-06-25)


### jplCloseRelease

* merge code from release to master and tag non-merge commit ([005a78e6639ccd791e0a0a96af3e4c95d4a6534f](https://github.com/red-panda-ci/jenkins-pipeline-library/commit/005a78e6639ccd791e0a0a96af3e4c95d4a6534f))



