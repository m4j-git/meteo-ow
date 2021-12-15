[![Java CI with Maven](https://github.com/m4j-git/meteo-ow/actions/workflows/maven.yml/badge.svg)](https://github.com/m4j-git/meteo-ow/actions/workflows/maven.yml)
[![CodeQL](https://github.com/m4j-git/meteo-ow/actions/workflows/codeql.yaml/badge.svg)](https://github.com/m4j-git/meteo-ow/actions/workflows/codeql.yaml)
# spring-boot-app for open-weather api

Description
-----------
....


Building
--------
##### Requirements
* Maven 3.6+
* Java 17+
* MariaDb 10+

##### Environment variables
* METEO_USER
* METEO_PASSWD

* OPENWEATHERMAP_API_KEY

##### /etc/hosts
* meteo-ow-host
* mysql-host

##### Check out (with submodule) and build:
    git clone --recurse-submodules -j8 git://github.com/m4j-git/meteo-ow.git
    ./make test
    ./make prod
    
