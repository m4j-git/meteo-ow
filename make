#!/bin/bash
set -e
java -version

# finding script folder
dir=`dirname $0`
absdir=`cd $dir; pwd`
cd $absdir

if [ "$1" = "build-prod" ]; then
  mvn clean install -P prod -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "build-stage" ]; then
  mvn clean install -P stage -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "build-dev" ]; then
  mvn clean install -P dev -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "test-dev" ]; then
  mvn clean test -P dev -Dorg.slf4j.simpleLogger.defaultLogLevel=warn -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "build-site" ]; then
  mvn site -P prod -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "build-docker" ]; then
  CONTAINER_NAME=meteo-ow
  echo -e "\nSet docker container name as ${CONTAINER_NAME}\n"
  IMAGE_NAME=${CONTAINER_NAME}:latest
  echo -e "\nSet docker image name as ${IMAGE_NAME}\n"
  PORT=8081
  echo -e "Set docker image PORT to ${PORT}\n"
  
  set +e
  echo -e "\nStop running Docker containers with image tag ${CONTAINER_NAME}, and remove them...n"
  docker stop $(docker ps -a | grep ${CONTAINER_NAME} | awk '{print $1}')
  docker rm $(docker ps -a | grep ${CONTAINER_NAME} | awk '{print $1}')
  set -e
  
  echo -e "\nDocker build image with name ${IMAGE_NAME}...\n"
  docker build -t ${IMAGE_NAME} -f Dockerfile .
  
  echo -e "\nStart Docker container of the image ${IMAGE_NAME} with name ${CONTAINER_NAME}...\n"
  docker run -it -d --restart unless-stopped \
      -p ${PORT}:${PORT} --network=host \
      -v /opt/meteo/meteo-ow/logs:/opt/meteo/logs \
      -e METEO_USER=${METEO_USER} \
      -e METEO_PASSWD=${METEO_PASSWD} \
      -e OPENWEATHERMAP_API_KEY=${OPENWEATHERMAP_API_KEY} \
      --name ${CONTAINER_NAME} \
      ${IMAGE_NAME} 

fi

if [ "$1" = "test-wrk" ]; then
  ./wrk -t12 -c12 -d30s http://meteo-ow-host:8081/meteo-ow/?geonameId=1
fi
