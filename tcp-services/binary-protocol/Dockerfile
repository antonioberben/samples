# Use the official maven/Java 8 image to create a build artifact.
FROM maven:3.6.0-jdk-8-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn package -DskipTests

# Use the official openjdk image for a lean production stage of our multi-stage build.
FROM openjdk:8-jre-slim
COPY --from=build /app/target/binary-protocol-1.0-SNAPSHOT.jar /app/binary-protocol.jar

WORKDIR /app

COPY ./start-app.sh /app/start-app.sh

EXPOSE 8080
CMD /app/start-app.sh
