#!/bin/bash

BUILD_NUMBER=$1
DOCKER_ID=$2
POSTGRES_PASSWORD=$3
POSTGRES_USERNAME=$4
POSTGRES_SERVER=$5
POSTGRES_DATABASE=$6


# stop all running containers with our web application
docker stop `docker ps -a | grep ${DOCKER_ID}/hackernews | awk '{print substr ($0, 0, 12)}'`
# remove all of those containers
docker rm `docker ps -a | grep ${DOCKER_ID}/hackernews | awk '{print substr ($0, 0, 12)}'`
# TODO remove old image
# get the newest version of the containerized web application and run it
docker pull ${DOCKER_ID}/hackernews:${BUILD_NUMBER}
docker run -d -ti -p 8080:8080 -p 80:80 ${DOCKER_ID}/hackernews:${BUILD_NUMBER} /start.sh ${POSTGRES_PASSWORD} ${POSTGRES_USERNAME} ${POSTGRES_SERVER} ${POSTGRES_DATABASE}

#docker run -e PASSWORD=${PASSWORD} -d -ti -p 8080:8080 ${DOCKER_ID}/hackernews:${BUILD_NUMBER} --jens' advise
