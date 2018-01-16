#!/bin/sh

echo $CATALINA_OPTS -cp "$LOLPAPERS_STUFF_PATH/lolpapers-cli.jar" de.jambonna.lolpapers.cli.UpdateContent | xargs java

