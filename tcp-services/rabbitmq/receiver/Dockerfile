# Use the official maven/Java 8 image to create a build artifact.
FROM maven:3.6.0-jdk-8-slim AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

# Use the official openjdk image for a lean production stage of our multi-stage build.
FROM openjdk:8-jdk-alpine
COPY --from=build /app/target/receiver-0.0.1-SNAPSHOT.jar /receiver.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/receiver.jar"]