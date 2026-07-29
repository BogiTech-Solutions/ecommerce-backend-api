# --- Stage 1: Build the application ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Copy dependency definition first for caching layer optimization
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and package application
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Runtime image ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create user and required upload directory with correct permissions
RUN addgroup -S appgroup && adduser -S appuser -G appgroup \
    && mkdir -p /app/uploads \
    && chown -R appuser:appgroup /app

USER appuser

# Copy built JAR from stage 1
COPY --from=builder /app/target/*.jar app.jar

# Expose API port
EXPOSE 8080

# Configure memory efficiency and start the Spring Boot app
ENTRYPOINT ["java", "-XX:+UseG1GC", "-jar", "app.jar"]