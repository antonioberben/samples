# Use an official Java runtime as a parent image
FROM openjdk:8

# Set the working directory in the container to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

ENV RMI_REGISTRY_PORT=1099
ENV RMI_SERVER_PORT=1100
ENV RMI_HOST=0.0.0.0

# Compile the Java files
RUN javac *.java

# Make ports available to the world outside this container
EXPOSE $RMI_REGISTRY_PORT
EXPOSE $RMI_SERVER_PORT

CMD rmiregistry $RMI_REGISTRY_PORT & java -Djava.rmi.server.hostname=$RMI_HOST -Djava.rmi.server.useLocalHostname=true HelloServer