#!/bin/sh

ver="$1"
if [ -z "$ver" ]
then
	echo "Give a release version"
	exit 1
fi

cp -v "../../../lolpapers-web/target/lolpapers-web-$ver.war" lolpapers.war
cp -v "../../../lolpapers-cli/target/lolpapers-cli-$ver.jar" stuff/lolpapers-cli.jar

