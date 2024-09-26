DOCKER_IMAGE=o2o-admin
DOCKER_TAG=latest

cd /home/ec2-user/o2o-admin

docker ps -q --filter "name=$DOCKER_IMAGE" | xargs -r docker stop
docker ps -a -q --filter "name=$DOCKER_IMAGE" | xargs -r docker rm
docker images -q $DOCKER_IMAGE | xargs -r docker rmi
docker build -t $DOCKER_IMAGE:$DOCKER_TAG .
docker run -d --name $DOCKER_IMAGE -p 8080:8080 $DOCKER_IMAGE:$DOCKER_TAG

