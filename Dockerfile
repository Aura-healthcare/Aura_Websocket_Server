
FROM openjdk:8u102-jre
MAINTAINER Laurent Ribiere

COPY WebSocket-server-0.1.0.jar /home/WebSocket-server-0.1.0.jar

CMD ["java","-jar","/home/WebSocket-server-0.1.0.jar"]

EXPOSE 8887
