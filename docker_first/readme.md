# Eseguo la build con
docker build -t first_example_docker .

# oppure aggiungendo la versione
docker build -t first_example_docker:v1 .

# verifica della presenza dell'image su docker:
docker image ls

# run dell'image
docker run -d --name example_docker_01 -P first_example_docker

# run dell'image esplicitando a docker che tutte le chiamate fatte sulla porta 8088 della macchina virtuale (192.168.99.100) vengano mappate sulla porta 80 del container:
docker run -d --name example_docker_01 -p 8088:80 first_example_docker

# verifica delle image/container attivi:
docker ps

# verifica dell'ip della docker machine, vm virtual toolbox (solitamente di default 192.168.99.100):
docker-machine ip default

# check pagina su dockercontainer:
http://192.168.99.100:8088/mypage.html 

# utility: cancellazione container e, successivamente, image:
docker ps
docker stop <containerid>
docker rm <containerid>
docker rmi <imageid>

# utility: cancellazione tutti container:
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

## clean up any resources — images, containers, volumes, and networks — that are dangling (not associated with a container):
docker system prune

## remove any stopped containers and all unused images:
docker system prune -a

## list e cancellazione immagini sospese/dangling:
docker images -f dangling=true
docker images purge

## list, cancellazione immagini singole e cancellazione di tutte le immagini
docker images -a
docker rmi ImageName1 ImageName2
docker rmi $(docker images -a -q)

## list e cancellazioni di tutti i container dopo la exit
docker ps -a -f status=exited
docker rm $(docker ps -a -f status=exited -q)

## cancellazione di container + volumi associati
docker rm -v container_name

## list e cancellazione dei volumi
docker volume ls
docker volume rm volume_name volume_name

## list e cancellazione di tutti i dangling volumi
docker volume ls -f dangling=true
docker volume prune
