# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Use cache mount to speed up Maven dependency downloads
RUN --mount=type=cache,target=/root/.m2 \
    mvn clean package -DskipTests

# Production stage
FROM pmpapplication/java21-alpine AS production

# Create a non-root user to run the application
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE ${SERVER_PORT}
VOLUME /tmp
ARG JAR_FILE=target/car-rental-service-0.0.1-SNAPSHOT.jar
COPY --from=build /app/${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
