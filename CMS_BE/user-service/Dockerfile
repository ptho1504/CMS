FROM openjdk:17-jdk-alpine

WORKDIR /app

# Install curl
RUN apk add curl

COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service-0.0.1-SNAPSHOT.jar

EXPOSE 8200 9090

CMD ["java", "-jar", "/app/user-service-0.0.1-SNAPSHOT.jar"]
