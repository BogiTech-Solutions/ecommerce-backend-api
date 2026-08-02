# --- Stage 1: Cache Dependencies & Build ---
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Dependency Caching Layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Compile Application
COPY src ./src
RUN mvn clean package -DskipTests

# --- Stage 2: Minimal Runtime Image ---
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Non-root security user setup
RUN addgroup -S appgroup && adduser -S appuser -G appgroup \
    && mkdir -p /app/uploads \
    && chown -R appuser:appgroup /app

USER appuser

# Copy JAR artifact
COPY --from=builder /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE ${PORT}

# Production-tuned JVM flags for containers
ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-XX:+UseG1GC", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", "app.jar"]

