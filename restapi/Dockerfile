# Build stage
FROM maven:3.8.4-openjdk-21 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src
# Package the application
RUN mvn clean package -DskipTests

# Run stage
FROM openjdk:21.0.1-slim-buster
# Set the working directory in the container
WORKDIR /app
# Copy the JAR from the build stage to the run stage
COPY --from=build /app/target/restapi-0.0.1-SNAPSHOT.jar .
# Expose the port the app runs on
EXPOSE 8081
# Run the JAR file
ENTRYPOINT ["java", "-jar", "restapi-0.0.1-SNAPSHOT.jar"]