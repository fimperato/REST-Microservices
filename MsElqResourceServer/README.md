## Microservices REST

#### Microservice di simulazione Eloqua Service (Server) per l'endpoint di recupero dei contact activities data 

```
https://docs.oracle.com/cloud/latest/marketingcs_gs/OMCAC/op-api-REST-1.0-data-activities-contact-%7Bid%7D-get.html
```

* Spring Boot 2
* Spring Data
* MongoDB
* JWT
* Maven
  
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
