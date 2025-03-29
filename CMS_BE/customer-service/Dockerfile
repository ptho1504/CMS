FROM openjdk:17-jdk-alpine

WORKDIR /app

# Install curl
RUN apk add curl

COPY target/customer-service-0.0.1-SNAPSHOT.jar /app/customer-service-0.0.1-SNAPSHOT.jar

EXPOSE 8300 9110

CMD ["java", "-jar", "/app/customer-service-0.0.1-SNAPSHOT.jar"]
