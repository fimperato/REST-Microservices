FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add bash

COPY target/MsElqResourceServer.jar $PROJECT_HOME/MsElqResourceServer.jar
CMD ["java", "-Dspring.data.mongodb.uri=mongodb://mongo_mselq_server:27017/msAppElqServerDB","-Djava.security.egd=file:/dev/./urandom","-jar","./MsElqResourceServer.jar"]
