## Microservices REST

#### Microservice di simulazione Eloqua Service (Server) per l'endpoint di recupero dei contact activities data 

```
https://docs.oracle.com/cloud/latest/marketingcs_gs/OMCAC/op-api-REST-1.0-data-activities-contact-%7Bid%7D-get.html
```

* Spring Boot 2
* Spring JPA
* JWT
* Maven
  
###### Start mongodb:

```
C:\Program Files\MongoDB\Server\3.4\bin>mongod --dbpath C:\mongodb_data\ms18_data
```

Use DB:

```
C:\Program Files\MongoDB\Server\3.4\bin>mongo
> use msAppElqServerDB
switched to db msAppElqServerDB
> db.createCollection("domain")
> show collections
> db.domain.insert({
  id: "1",
  domain: "imperato_domain",
  visible: true
})
```