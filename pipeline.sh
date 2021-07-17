echo "Current dir"
echo $PWD
echo "Changing to base dir"
cd /docker/railiac/rlc-rest/app
echo $PWD
echo "Stopping and cleaning running image"
make clean
echo "Building docker image"
make build-docker-image
echo "Deploying new container"
make deploy
disown
echo "App booted"
