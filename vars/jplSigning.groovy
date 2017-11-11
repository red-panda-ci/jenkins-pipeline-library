/**

App signing management

Parameters:

* cfg jplConfig class object
* String signingRepository The repository (github, bitbucket, whatever) where the signing information lives
* String signingPath Relative path to locate the signing data within signing repository
* String artifactPath Path to the artifact file to be signed, relative form the build workspace

cfg usage:

* cfg.projectName

Notes:

* The artifactPath must be an unsigned APK, it's name should match the pattern "*-unsigned.apk"
* Your Jenkins instance must have read access to the repository containing signing data
* The signed artifact will be placed on the same route of the artifact to be signed, and named "*-signed.apk"
* The repository structure sould be like this:

    * Must have a "credentials.json" file with this content:

        {
            "STORE_PASSWORD": "store_password_value",
            "KEY_ALIAS": "key_alias_value",
            "KEY_PASSWORD": "key_password_value",
            "ARTIFACT_SHA1": "D7:22:FF:...."
        }

    * Must have a "keystore.jks", as the signing keystore file

    Both file should be placed in the a repository path, wich is informed with the "signingPath" parameter

*/
def call(cfg, String signingRepository, String signingPath, String artifactPath) {
    if (!artifactPath.endsWith("-unsigned.apk")) {
        error ("jplSign: should have an unsigned APK wich ends with 'signed-apk'")
    }
    sh "ci-scripts/.jenkins_library/bin/signApk.sh --sdkVersion='${cfg.projectName}' --signingRepository='${signingRepository}' --signingPath=${signingPath}  --artifactPath='${artifactPath}'"
}
