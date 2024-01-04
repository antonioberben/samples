# Use an official Java runtime as a parent image
FROM openjdk:8

# Set the working directory in the container to /app
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Set the RMI port
ENV RMI_PORT=1099

# Compile the Java files
RUN javac *.java

# Run the client
CMD /app/start-client.sh