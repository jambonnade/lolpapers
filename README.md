# LolPapers

Source code for personnal project *LolPapers*.

It is made of a Java webapp, targeted for a Tomcat container (although standard features are used). It uses Hibernate and a bit of JDBC for persistence. MySQL is the only possible backend but others could be easily implemented thanks to a DAO layer.

Since some operations may need to be done by command line, the project is divided in 3 Maven projects :
* **lolpapers-core** : core/business code, without any web dependencies (although it knows there is a webapp)
* **lolpapers-web** : the webapp
* **lolpapers-cli** : command line programs

## Give it a try
Some Docker *stacks* are given as examples to make a working environment. Could be taken as-is ore altered for needs.
Note : [initiate Docker swarm mode](https://docs.docker.com/get-started/part3/#run-your-new-load-balanced-app) to be able to use Docker stacks.

These stacks are at least made of a *Tomcat service* and a *MySQL service*. For conviniency, a custom image has been made for the Tomcat service : it contains the WAR and everything needed. It also waits for the MySQL server to be ready before starting, so it may take some time before Tomcat accepts connections.


### Ephemeral state01
This stack is not persistent (deleting the stack removes everything) and starts with a predefined state (database + files) ; a good choice to give it a try. It is also used by the *integration tests* provided (see below).

	cd docker/stacks/ephemeral-state01/
	docker stack deploy -c docker-compose.yml lolp

The webapp url is [http://127.0.0.1:8080/lolpapers/](http://127.0.0.1:8080/lolpapers/)

State01 has the following :
* There are some *base articles* to be able to create new *article templates*
* There are two existing users : Utilisateur 1 (id 1) and Utilisateur 2 (id 2)

To remove the stack : `docker stack rm lolp`

### Persistent

This stack mounts directories from your system (`tomcat-var` and `mysql-var`) to keep state between restarts. Before starting the stack, you'll need to init these directories with `make-var-dirs.sh`

	cd docker/stacks/persistent/
	./make-var-dirs.sh
	docker stack deploy -c docker-compose.yml lolp

The webapp url is [http://127.0.0.1:8080/lolpapers/](http://127.0.0.1:8080/lolpapers/)

There is also a commented configuration for an Nginx container that acts as a reverse proxy like in production. In this case, the url is the one you configure. You'll need to add the chosen domain name to your *hosts* file.

To be able to create new articles, you'll need to have *base articles*, which are created using one of the command line programs. The content-update.sh script in the Tomcat container does this. It is called by a cronjob in production like this :

	docker exec $(docker ps -f name="stackname_tomcatservicename." -q) content-update.sh

To remove the stack : `docker stack rm lolp`

### How to login
You'll need to login to try the important features (make an article template, replace placeholders).

Without setting up a Twitter app to use twitter login (which is typically the case), you can login by making your browser send the **dev key** special HTTP header. You'll need to install a browser extension for that like :
* [Modify Header Value (Chrome)](https://chrome.google.com/webstore/detail/modify-header-value-http/cbdibdfhahmknbkkojljfncpnhmacdek?hl=fr)
* [Modify Header Value (Firefox)](https://addons.mozilla.org/en-US/firefox/addon/modify-header-value/)

The header is `X-Dev-Key` and the value by default is `chips`.

There is now a form at the bottom of each page allowing you to login : just enter a user id to login to an existing user, or a name to create a new user.

### Recreate the Tomcat image
In case you don't want to use the existing image `jambonnade/lolpapers-tomcat`, you can recreate it :
* compile the lolpapers-web project to make the WAR
* compile the lolpapers-cli project in a fat JAR
* copy the WAR and JAR to the right path in `docker/images/lolpapers-tomcat/` (use `copy-jars.sh` for that)
* build the image with `docker build -t yourimage .`
* change the `docker-compose.yml` files to use this image instead

### Run integration tests

Integration tests are in `lolpapers-ci` project. As an example, a single test has been written : a user scenario showing the main features and important cases. This scenario needs to be run from the *state01* state. Some configuration has to be passed via system properties.

* install [PhantomJS](http://phantomjs.org/) somewhere on your system
* run the *ephemeral-state01* stack
* run the tests in lolpapers-ci project (change phantomJS path) :


	cd lolpapers-ci
	mvn -Dde.jambonna.lolpapers.ci.phantomJSPath=/path/to/phantomjs/bin/phantomjs -Dde.jambonna.lolpapers.ci.devKey=chips -Dde.jambonna.lolpapers.ci.baseUrl=http://127.0.0.1:8080/lolpapers/ test

Check the generated screenshots in `webdriver-var` directory.