FROM openjdk:19-alpine
WORKDIR /opt
ENV PORT 8080
EXPOSE 8080
COPY build/libs/*.jar /opt/
ENTRYPOINT ["java","-jar","api-gateway-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT exec java $JAVA_OPTS -jar api-gateway-0.0.1-SNAPSHOT.jar
