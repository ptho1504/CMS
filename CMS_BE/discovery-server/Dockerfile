FROM maven:3.8.6-openjdk-18-slim AS BUILD
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package


FROM openjdk:18.0-slim
RUN apt-get update && apt-get install -y curl  # Install curl
EXPOSE 8761
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]