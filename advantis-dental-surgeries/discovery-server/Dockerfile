# ================================
# Stage 1: Build the application
# ================================
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Set working directory inside container
WORKDIR /app

# Copy all source code to the container
COPY . .

# Build Spring Boot JAR (skip tests for faster build)
RUN mvn clean package -DskipTests


# ================================
# Stage 2: Run the application
# ================================
FROM eclipse-temurin:21-jdk

# Set working directory for runtime
WORKDIR /app

# Copy the JAR built in the first stage
COPY --from=build /app/target/*.jar app.jar

# Expose the Eureka port
EXPOSE 8761

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
