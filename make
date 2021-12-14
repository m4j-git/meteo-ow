#!/bin/bash
set -e
java -version

# finding script folder
dir=`dirname $0`
absdir=`cd $dir; pwd`
cd $absdir

if [ "$1" = "prod" ]; then
  mvn clean install -P prod,mysql -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "stage" ]; then
  mvn clean install -P stage,mysql -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "dev" ]; then
  mvn clean install -P dev,h2 -T 1C -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "test" ]; then
  mvn clean test -P dev,h2 -T 1C -Dorg.slf4j.simpleLogger.defaultLogLevel=warn -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "site" ]; then
  mvn site -P prod,mysql -Dmaven.test.skip -Dorg.slf4j.simpleLogger.defaultLogLevel=info -DargLine="-Xms1024m -Xmx8192m"
fi

if [ "$1" = "docker-build" ]; then
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
      --name ${CONTAINER_NAME} \
      ${IMAGE_NAME}
fi
 
