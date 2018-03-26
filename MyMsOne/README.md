## Microservices REST

#### Microservice One

* Spring Boot 2
* Spring JPA
* JWT
* Maven
  
###### docker:
[local-path]\MyMsOne>docker build -t mymsone_docker .

[local-path]\MyMsOne>docker run -d -p 8094:8084 mymsone_docker

###### docker test:
http://192.168.99.100:8094/userManagement
