FROM openjdk:17-jdk-alpine

WORKDIR /app

# Install curl
RUN apk add curl

COPY target/payment-service-0.0.1-SNAPSHOT.jar /app/payment-service-0.0.1-SNAPSHOT.jar

EXPOSE 8600 9140

CMD ["java", "-jar", "/app/payment-service-0.0.1-SNAPSHOT.jar"]
