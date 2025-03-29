FROM openjdk:17-jdk-alpine

WORKDIR /app

# Install curl
RUN apk add curl

COPY target/order-service-0.0.1-SNAPSHOT.jar /app/order-service-0.0.1-SNAPSHOT.jar

EXPOSE 8500 9120

CMD ["java", "-jar", "/app/order-service-0.0.1-SNAPSHOT.jar"]
