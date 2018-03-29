FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

COPY target/MsElqClient.jar $PROJECT_HOME/MsElqClient.jar
CMD ["java", "-Dspring.data.mongodb.uri=mongodb://mongo_mselq_client:27018/msAppClientDB","-Djava.security.egd=file:/dev/./urandom","-jar","./MsElqClient.jar"]
