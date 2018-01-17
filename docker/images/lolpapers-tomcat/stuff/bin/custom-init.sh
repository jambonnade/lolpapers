#!/bin/sh

cmd="$@"


export CATALINA_OPTS="
	-Dde.jambonna.lolpapers.conf.app.baseUrl=\"$LOLPAPERS_BASE_URL\"
	-Dde.jambonna.lolpapers.conf.app.basePath=\"$LOLPAPERS_BASE_URL_PATH\"
	-Dde.jambonna.lolpapers.conf.app.varPath=\"$LOLPAPERS_VAR_PATH\"
	-Dde.jambonna.lolpapers.conf.datasource.url=\"jdbc:mysql://$LOLPAPERS_DS_HOST/$LOLPAPERS_DS_DB?useSSL=false&useUnicode=true&allowMultiQueries=true\"
	-Dde.jambonna.lolpapers.conf.datasource.username=\"$LOLPAPERS_DS_USER\"
	-Dde.jambonna.lolpapers.conf.datasource.password=\"$LOLPAPERS_DS_PWD\"
	-Dde.jambonna.lolpapers.conf.twitter.signin.consumerKey=\"$LOLPAPERS_TWITTER_SIGNIN_API_KEY\"
	-Dde.jambonna.lolpapers.conf.twitter.signin.consumerSecret=\"$LOLPAPERS_TWITTER_SIGNIN_API_SECRET\"
	-Dde.jambonna.lolpapers.conf.twitter.main.consumerKey=\"$LOLPAPERS_TWITTER_MAIN_API_KEY\"
	-Dde.jambonna.lolpapers.conf.twitter.main.consumerSecret=\"$LOLPAPERS_TWITTER_MAIN_API_SECRET\"
	-Dde.jambonna.lolpapers.conf.twitter.main.accessToken=\"$LOLPAPERS_TWITTER_MAIN_ACCESS_TOKEN\"
	-Dde.jambonna.lolpapers.conf.twitter.main.accessTokenSecret=\"$LOLPAPERS_TWITTER_MAIN_ACCESS_SECRET\"
	-Dde.jambonna.lolpapers.web.assetsVer=\"$LOLPAPERS_WEB_DEV_ASSETSVER\"
	-Dde.jambonna.lolpapers.web.devKey=\"$LOLPAPERS_WEB_DEV_KEY\""


scripts_dir="$LOLPAPERS_STUFF_PATH/init"
if [ -d "$scripts_dir" ]
then
	echo "Executing user scripts..."
	for f in $(find "$scripts_dir" -type f -name '*.sh')
	do
		echo "$f..."
		$f
	done
fi


until mysql -h "$LOLPAPERS_DS_HOST" -u "$LOLPAPERS_DS_USER" "$LOLPAPERS_DS_DB" "-p${LOLPAPERS_DS_PWD}" -e 'SELECT 1' > /dev/null
do
  echo "Mysql is unavailable - sleeping"
  sleep 1
done

echo "Mysql is up - executing command $cmd"
exec $cmd

