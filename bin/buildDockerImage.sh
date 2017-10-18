#!/bin/bash

buildDockerImage="$(basename "$0" | sed -e 's/-/ /')"
HELP="Usage: $buildDockerImage --sdkVersion=xx.y.z

Build docker image with Android SDK tools xx.y.z and fastlane installed on it

Options:
  --sdkVersion=xx.y.z         # build docker image called 'ci-scripts:xx.y.z'
  --help                      # prints this

Examples:
  $buildDockerIMage 23.0.3    # Build docker image called 'ci-scripts:23.0.3'
"

cd "$(dirname "$0")/../docker"

# Parse variables
sdkversion=""
echo $1
while [ $# -gt 0 ]; do
  case "$1" in
    --sdkVersion=*)
      sdkVersion="${1#*=}"
      ;;
    --h|\?|--help)
      echo "$HELP"
      exit 0
      ;;
    *)
      printf 'ERROR: Unknown option: %s\n' "$1" >&2
      echo "$HELP"
      exit 1
  esac
  shift
done

# Check sdk version
if [ -f ../../../Dockerfile.tail ]
then
    cp -p android-base/Dockerfile ../../..
    cat ../../../Dockerfile.tail >> ../../../Dockerfile
    dockerImageName="ci-scripts:${sdkVersion}"
    dockerImageFolder="../../.."
else
    if [ -f ../../docker/${sdkVersion}/Dockerfile ]
    then
        dockerImageName="ci-scripts:${sdkVersion}"
        dockerImageFolder="../../docker/${sdkVersion}"
    else
        if [ -f android-sdk-${sdkVersion}/Dockerfile ]
        then
            dockerImageName="ci-scripts:${sdkVersion}"
            dockerImageFolder="android-sdk-${sdkVersion}"
        else
            echo "Unknown SDK version: ${sdkVersion}"
            echo
            echo "$HELP"
            exit 1
        fi
    fi
fi

# Check user home debug.keystore
echo -n "Searching for .android/debug.keystore file..."
if [ -f ../../../.android/debug.keystore ]
then
    androidFolder=../../../.android
    echo " found in project. Use '.android' of root project folder"
else
    if [ -f ~/.android/debug.keystore ]
    then
        androidFolder=~/.android
        echo " found global. Use global '~/.android' of the user home folder"
    else
        echo " not found. Can't continue"
        exit 1
    fi
fi

# Prepare temporary resources
buildTempFolder=/tmp/ci-scripts.${sdkVersion}.${RANDOM}
echo "Building docker image"
echo "- Docker image name...: ${dockerImageName}"
echo "- Docker image folder.: ${dockerImageFolder}"
echo "- Build temp folder...: ${buildTempFolder}"
mkdir -p ${buildTempFolder}/.android || exit 1
cp ${dockerImageFolder}/Dockerfile ${buildTempFolder} || exit 3
cp ${androidFolder}/* ${buildTempFolder}/.android/ 2> /dev/null || :

# Docker image build (container image files involved)
docker build -t ${dockerImageName} ${buildTempFolder}

# Remove temporary resources
rm ${buildTempFolder}/.android/* ${buildTempFolder}/Dockerfile || exit 5
rmdir ${buildTempFolder}/.android ${buildTempFolder} || exit 6
