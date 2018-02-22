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
