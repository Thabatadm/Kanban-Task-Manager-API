# Stage 1: Build process using Maven and OpenJDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and source code to the container
COPY . .

# Build the application, skipping unit tests for faster deployment
RUN mvn clean package -DskipTests

# Stage 2: Lightweight Runtime Environment
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the executable JAR from the build stage
COPY --from=build /target/*.jar app.jar

# Application port documentation
EXPOSE 8080

# Execution command for the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]