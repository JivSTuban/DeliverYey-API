# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Add Maven dependencies (not necessary if you already built the JAR externally)
COPY target/DeliverYey-0.0.1-SNAPSHOT.jar DeliverYey-0.0.1-SNAPSHOT.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "DeliverYey-0.0.1-SNAPSHOT.jar"]
