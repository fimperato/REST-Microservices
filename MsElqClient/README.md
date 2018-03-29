## Microservices REST

#### Microservice di simulazione Eloqua Client

* Spring Boot 2
* Spring Data
* MongoDB
* JWT
* Maven
  
##### Uso del Client in JWT auth mode:

Contact (id) = 14

```
http://localhost:8086/activity/JWT/getActivities/14
```

Richiesta / Update token JWT (memorizzato fino ad expired time): 

```
http://localhost:8086/jwt/getAndSaveJwtInfo
```

##### Uso del Client no-auth mode:

Contact (id) = 14

```
http://localhost:8086/activity/getActivities/14?isMock=false
```

###### Start mongodb:

```
C:\Program Files\MongoDB\Server\3.4\bin>mongod --dbpath C:\mongodb_data\ms18_data
```

###### docker:
*Maven project. Passare il local ip come parametro all'application properties, <docker ip> di defult è 192.168.99.100
```
mvn clean package -Dmaven.test.skip=true -DLOCAL_IP=<your local ip> -DDOCKER_IP=192.168.99.100
```

Single container in docker file:
```
[local-path]\MsElqClient>docker build -t mselq_client_docker .

[local-path]\MsElqClient>docker run -d -p 8086:8086 mselq_client_docker
```

###### docker compose with mongo image
```
[project path]>docker-compose up
```

*Options con .env file o con il comando (dove <command> è up in tal caso):
```
LOCAL_IP=<your ip here> ENV2=$ENV2 ENVn=$ENVn docker-compose <command>
```

Nota su data volume con mongoDB e virtualBox:
```
MongoDB requires a filesystem that supports fsync() on directories. For example, HGFS and Virtual Box’s shared folders do not support this operation.
```

###### docker test:
Endpoint per testare lo start del container:

```
http://192.168.99.100:8086/activity/activityController
```

Check sui dati presenti sul db nosql del container attraverso un client verso:
```
host: 192.168.99.100
host: mongo_mselq_client
port: 27017
```

Ispezione dei dati possibile anche attraverso il mongoclient su docker container (da compose):
```
http://192.168.99.100:3300/browseCollection
```

###### postgresql (external) db:
Test start up con l'endpoint (192.168.99.100 default ip docker container):
```
http://192.168.99.100:[docker-app-port]/postgres/users
```
