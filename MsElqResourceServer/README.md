## Microservices REST

#### Microservice di simulazione Eloqua Service (Server) per l'endpoint di recupero dei contact activities data 

```
https://docs.oracle.com/cloud/latest/marketingcs_gs/OMCAC/op-api-REST-1.0-data-activities-contact-%7Bid%7D-get.html
```

* Spring Boot 2
* Spring Data
* MongoDB (local, database-as-a-service version)
* JWT
* Maven
* Swagger 2
* Docker 

###### Start mongodb:

```
C:\Program Files\MongoDB\Server\3.4\bin>mongod --dbpath C:\mongodb_data\ms18_data
```

###### Use DB - domain (test) collection:

```
C:\Program Files\MongoDB\Server\3.4\bin>mongo
> use msAppElqServerDB
switched to db msAppElqServerDB
> db.createCollection("domain")
> show collections
> db.domain.remove({})
> db.domain.insert({
  domainCode: 209,
  domain: "imperato_domain",
  visible: true
})
```

###### Use DB - activity collection:

```
C:\Program Files\MongoDB\Server\3.4\bin>mongo
> use msAppElqServerDB
switched to db msAppElqServerDB
> db.createCollection("elqActivity")
> show collections
> db.elqActivity.remove({})
> db.elqActivity.insert({
  id: "1",
  activityDate: "1519210423520",
  activityType: "Completed",
  assetType: "Email",
  contact: "151"
})
```

###### Use Swagger 2:

```
http://[springboot-app-host]:[port]/swagger-ui.html
```

###### docker:
*Maven project. Passare il local ip come parametro all'application properties
```
mvn clean package -Dmaven.test.skip=true -DLOCAL_IP=<your local ip>
```

Single container in docker file:
```
[local-path]\MsElqResourceServer>docker build -t ms_elq_resource_server .

[local-path]\MsElqResourceServer>docker run -d -p 8095:8085 ms_elq_resource_server
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
http://192.168.99.100:8085/api/REST/1.0/data/activityController
```

Check sui dati presenti sul db nosql del container attraverso un client verso:
```
host: 192.168.99.100
host: mongo_mselq_server
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

###### tag e publish docker image:

    [local-path]\MsElqResourceServer>docker build --tag fimperato/ms_elq_resource_server:v1.1 .

Inserimento di user e password del proprio repo docker:
 
    [local-path]\MsElqResourceServer>docker login
    
Push della specifica tag version su repo docker:
   
    [local-path]\MsElqResourceServer>docker push fimperato/ms_elq_resource_server:v1.1
    
Image docker disponibile alla seguente directory repo:

    https://hub.docker.com/r/fimperato/ms_elq_resource_server/

