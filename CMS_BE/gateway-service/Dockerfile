FROM openjdk:17-jdk-alpine

WORKDIR /app

# Install curl
RUN apk add curl

COPY target/gateway-service-0.0.1-SNAPSHOT.jar /app/gateway-service-0.0.1-SNAPSHOT.jar

EXPOSE 8222 9200

CMD ["java", "-jar", "/app/gateway-service-0.0.1-SNAPSHOT.jar"]
