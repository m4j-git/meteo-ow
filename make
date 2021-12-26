#!/bin/bash
set -e
java -version

# finding script folder
dir=`dirname $0`
absdir=`cd $dir; pwd`
cd $absdir

skip='-Dmaven.test.skip -DskipITs'

MODULE_NAME=meteo-ow
echo "make $MODULE_NAME"

show_help(){
    echo -e "Usage: ./make build|docker|check|deploy|test"
    exit
}
 
docker_stop(){
  set +e
  echo -e "\nStop running Docker containers with image tag ${MODULE_NAME}, and remove them...n"
  docker stop $(docker ps -a | grep ${MODULE_NAME} | awk '{print $1}')
  docker rm $(docker ps -a | grep ${MODULE_NAME} | awk '{print $1}')
  set -e
 } 
  
docker_build(){
  IMAGE_NAME=${MODULE_NAME}:latest
  docker_stop 
  
  echo -e "\nDocker build image with name ${IMAGE_NAME}...\n"
  docker build -t ${IMAGE_NAME} -f Dockerfile .
  
  echo -e "\nStart Docker container of the image ${IMAGE_NAME} with name ${MODULE_NAME}...\n"
  docker run -it -d --restart unless-stopped \
      -p 8081:8081 --network=host \
      -v /opt/meteo/meteo-ow/logs:/opt/meteo/logs \
      -e METEO_USER=${METEO_USER} \
      -e METEO_PASSWD=${METEO_PASSWD} \
      -e OPENWEATHERMAP_API_KEY=${OPENWEATHERMAP_API_KEY} \
      --name ${MODULE_NAME} \
      ${IMAGE_NAME} 
}

if [ "$1" = "build-prod" ]; then
  mvn clean install -P prod  $skip   
fi

if [ "$1" = "build-stage" ]; then
  mvn clean install -P stage $skip   
fi

if [ "$1" = "build-dev" ]; then
  mvn clean install -P dev $skip   
fi

if [ "$1" = "test-it" ]; then
  mvn clean test  verify -P dev  
fi

if [ "$1" = "build-site" ]; then
  mvn site -P prod $skip   
fi

if [ "$1" = "docker-build" ]; then
  docker_build
fi

if [ "$1" = "test-stress" ]; then
  ./wrk -t12 -c12 -d30s http://meteo-ow-host:8081/meteo-ow/?geonameId=1
fi

if [ "$1" = "docker-stop" ]; then
  docker_stop
fi

