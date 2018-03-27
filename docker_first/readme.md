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

# cancellazione container e, successivamente, image:
docker ps
docker stop <containerid>
docker rm <containerid>
docker rmi <imageid>