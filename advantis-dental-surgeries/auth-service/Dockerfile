# --- BUILD STAGE ---
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# --- RUN STAGE ---
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# ✅ Optional: pass DB connection as env vars
ENV SPRING_R2DBC_URL=r2dbc:postgresql://ads-postgres:5432/authdb
ENV SPRING_R2DBC_USERNAME=postgres
ENV SPRING_R2DBC_PASSWORD=admin

EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]
