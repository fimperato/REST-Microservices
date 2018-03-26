FROM java:8
VOLUME /tmp
ADD target/MsElqResourceServer.jar MsElqResourceServer0326.jar
RUN bash -c 'touch /MsElqResourceServer0326.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/MsElqResourceServer0326.jar"]