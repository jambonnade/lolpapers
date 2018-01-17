FROM tomcat:8-jre8-alpine

ENV \
	LOLPAPERS_STUFF_PATH=/usr/local/lolpapers \
	LOLPAPERS_VAR_PATH=/var/local/lolpapers \
	LOLPAPERS_BASE_URL= \
	LOLPAPERS_BASE_URL_PATH=/lolpapers/ \
	LOLPAPERS_DS_HOST=mysql-db-host \
	LOLPAPERS_DS_USER=lolpapers \
	LOLPAPERS_DS_PWD=chips \
	LOLPAPERS_DS_DB=lolpapers \
	LOLPAPERS_WEB_DEV_KEY=chips \
	LOLPAPERS_WEB_DEV_ASSETSVER= \
	LOLPAPERS_TWITTER_SIGNIN_API_KEY= \
	LOLPAPERS_TWITTER_SIGNIN_API_SECRET= \
	LOLPAPERS_TWITTER_MAIN_API_KEY= \
	LOLPAPERS_TWITTER_MAIN_API_SECRET= \
	LOLPAPERS_TWITTER_MAIN_ACCESS_TOKEN= \
	LOLPAPERS_TWITTER_MAIN_ACCESS_SECRET=


ENV PATH $LOLPAPERS_STUFF_PATH/bin:$PATH


ADD http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.42/mysql-connector-java-5.1.42.jar "$CATALINA_HOME/lib/"
RUN apk update; apk add mysql-client

COPY stuff "$LOLPAPERS_STUFF_PATH"
RUN chmod +x "$LOLPAPERS_STUFF_PATH/bin/"*.sh

COPY server.xml "$CATALINA_HOME/conf/"
COPY lolpapers-context.xml "$CATALINA_HOME/conf/Catalina/localhost/lolpapers.xml"
COPY lolpapers.war "$CATALINA_HOME/webapps/lolpapers.war"

CMD [ "custom-init.sh", "catalina.sh", "run" ]

# vim: set syntax=dockerfile:
