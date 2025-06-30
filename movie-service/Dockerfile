# Use OpenJDK base image
FROM openjdk:17-jdk-slim

# Create a directory inside container
WORKDIR /app

# Copy built jar (you must build this beforehand)
COPY target/*.jar app.jar

# Run the jar
ENTRYPOINT ["java", "-jar", "app.jar"]