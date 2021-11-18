[![Java CI with Maven](https://github.com/m4j-git/meteo-ow/actions/workflows/maven.yml/badge.svg)](https://github.com/m4j-git/meteo-ow/actions/workflows/maven.yml)

# spring-boot-app for open-weather api

Description
-----------
....

Building
--------
### Requirements
* Maven 3+
* Java 11+
* MariaDb 5+

Check out (with submodule) and build:

    git clone --recurse-submodules -j8 git://github.com/m4j-git/meteo-ow.git
    ./make test
    ./make prod

### environment variables
* METEO_USER
* METEO_PASSWD
* MYSQL_HOST
* MYSQL_PORT
* MYSQL_USER
* MYSQL_PASSWD
* OPENWEATHERMAP_API_KEY

### /etc/hosts
* meteohost