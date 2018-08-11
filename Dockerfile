FROM gradle:4.9.0-jdk8-alpine
ADD src src
ADD build.gradle .
RUN gradle test fatJar

FROM openjdk:8u102-jre
COPY --from=0 /home/gradle/build/libs/WebSocketServer-1.0.0-SNAPSHOT.jar /home/WebSocketServer-1.0.0-SNAPSHOT.jar
EXPOSE 8887
LABEL maintainer="laurent.ribiere@aura.healthcare"
CMD ["java","-jar","/home/WebSocketServer-1.0.0-SNAPSHOT.jar","-c/home/conf/wsserver.properties", "-d"]
