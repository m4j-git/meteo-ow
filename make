#!/bin/bash
set -e
java -version

# finding script folder
dir=`dirname $0`
absdir=`cd $dir; pwd`
cd $absdir

if [ "$1" = "prod" ]; then
  mvn clean
  mvn install -P prod,mysql -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "old" ]; then
  mvn clean
  mvn install -P prod,postgres -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "stage" ]; then
  mvn clean
  mvn install -P stage,mysql -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "dev" ]; then
  mvn clean
  mvn install -P dev,h2 -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "test" ]; then
  #mvn clean
  mvn test -P dev,h2 -T 1C -Dorg.slf4j.simpleLogger.defaultLogLevel=warn -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "site" ]; then
  mvn site -P prod,mysql -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "kill" ]; then
  rm .attach_pid*
  rm -r catalina.home_IS_UNDEFINED
  ps ax | grep tomcat | awk '{system("kill -KILL " $1 )}'
fi

if [ "$1" = "rm" ]; then
  find . -name "*.iml" -delete
  find . -name ".attach_pid*" -delete
  find . -name "catalina.home_IS_UNDEFINED" -delete
  find . -name "*.project" -delete
  find . -name "buildNumber.properties" -delete
  find . -name ".classpath" -delete
  find . -name ".factorypath" -delete
  find . -path "*.settings*" -delete
  find . -path "*target*" -delete
  rm -r catalina.home_IS_UNDEFINED
  rm -r .attach_pid*
fi

if [ "$1" = "deploy" ]; then
  echo 'Copy files...'

  scp target/meteo-provider.war root@fir:/opt/tomcat/9.0/webapps

  echo 'Restart server...'

  ssh -tt root@fir <<EOF
 
 systemctl stop  tomcatd
 
 sleep 1s
 
 if cd /opt/tomcat/9.0/logs/; then rm -f *; fi
 if cd /opt/tomcat/9.0/webapps/; then rm -rf meteo-provider; fi
 
 
 systemctl restart  tomcatd
 exit
EOF

  sleep 3s

  wget -O wget.txt http://fir:8080/meteo-provider
  rm wget.txt

  echo 'Bye'
fi

#git submodule foreach --recursive 'git push' && git push


#mvn spring-boot:run

#mvn versions:display-dependency-updates
#mvn pmd:pmd
#mvn pmd:cpd
#mvn findbugs:gui
#mvn jxr:jxr

#chromium --disable-web-security --user-data-dir="/tmp/chrome_dev_test" -–allow-file-access-from-files  --disable-gpu
