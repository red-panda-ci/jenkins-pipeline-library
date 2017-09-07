/**

  App signing management

  Parameters:
  * cfg jplConfig class object
  * String signingRepository The repository (github, bitbucket, whatever) where the signing information lives
  * String signingPath Relative path to locate the signing data within signing repository
  * String artifactPath Path to the artifact file to be signed, relative form the build workspace

  cfg usage:
  * cfg.signing.*

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
def call(cfg,String repository, String signingPath, String artifactPath) {
    expectedSigningItem = [ "STORE_PASSWORD","KEY_ALIAS","KEY_PASSWORD","ARTIFACT_SHA1"]
    repositoryBasePath = "ci-scripts/.signing_repository"

    if (!artifactPath.endsWith("-unsigned.apk")) {
        error ("jplSign: should have an unsigned APK wich ends with 'signed-apk'")
    }
    signedArtifactPath = artifactPath.replace("-unsigned.apk","-signed.apk")

    // Clone signing repo
    sh "rm -rf ${repositoryBasePath} && mkdir -p ${repositoryBasePath} && git clone ${repository} ${repositoryBasePath}"

    // Read items from credentials file
    signingItem = readJSON file: "${repositoryBasePath}/${signingPath}/credentials.json"

    // Check presence of all expected items
    for (int i = 0; i < expectedSigningItem.size(); i++) {
        key = expectedSigningItem[i]
        if (!signingItem.containsKey(key)) {
            error("jplSigning: credentials config file should contain key ${key}")
        }
    }

    // Sign
    sh "jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 -keystore ${repositoryBasePath}/${signingPath}/keystore.jks -storepass ${signingItem.STORE_PASSWORD} -keypass ${signingItem.KEY_PASSWORD} -signedjar ${signedArtifactPath} ${artifactPath} ${signingItem.KEY_ALIAS}"

    // Remove sign repository
    sh "rm -rf ${repositoryBasePath}"

    // Verify
    sh "keytool -list -printcert -jarfile ${signedArtifactPath}"
    signedArtifactSHA1 = sh (
        script: "keytool -list -printcert -jarfile ${signedArtifactPath}|grep SHA1",
        returnStdout: true
    ).trim().tokenize(" ")

    if (signedArtifactSHA1[0] != "SHA1:") {
        error("jplSigning: error during sign verification")
    }
    if (signedArtifactSHA1[1] != signingItem.ARTIFACT_SHA1) {
        error("jplSigning: signed artifact doesn't match. Expected ${signingItem.ARTIFACT_SHA1} but got ${value}")
    }
}
