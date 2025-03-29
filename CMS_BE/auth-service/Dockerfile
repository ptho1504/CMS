FROM maven:3.8.6-eclipse-temurin-17 as build
WORKDIR /app

# Install Protobuf manually
RUN apt-get update && apt-get install -y protobuf-compiler

COPY pom.xml .
COPY src ./src

RUN mvn protobuf:compile
RUN mvn clean package -DskipTests




# Stage 2: Create the final lightweight image
# FROM  openjdk:17-jdk-alpine3.14 as production
# WORKDIR /app
# RUN apk add curl
# COPY --from=build /app/target/* app.jar
# EXPOSE 8100 9000
# CMD ["java", "-jar", "app.jar"]
