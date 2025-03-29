FROM openjdk:17-jdk-alpine

WORKDIR /app

# Install curl
RUN apk add curl

COPY target/product-0.0.1-SNAPSHOT.jar /app/product-0.0.1-SNAPSHOT.jar

EXPOSE 8400 9130

CMD ["java", "-jar", "/app/product-0.0.1-SNAPSHOT.jar"]
