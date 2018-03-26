FROM java:8
VOLUME /tmp
ADD target/MyMsOne.jar MyMsOne20180325.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/MyMsOne20180325.jar"]