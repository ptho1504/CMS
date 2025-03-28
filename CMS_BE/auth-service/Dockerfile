FROM openjdk:17-jdk-alpine

WORKDIR /app

# Install curl
RUN apk add curl

COPY target/auth-service-0.0.1-SNAPSHOT.jar /app/auth-service-0.0.1-SNAPSHOT.jar

EXPOSE 8100 9000

CMD ["java", "-jar", "/app/auth-service-0.0.1-SNAPSHOT.jar"]
