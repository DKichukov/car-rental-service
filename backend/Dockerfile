# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
# Download dependencies in a separate layer
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests
RUN rm -rf /root/.m2 /app/src /app/pom.xml

# Production stage
FROM pmpapplication/java21-alpine AS production
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
RUN chown -R appuser:appgroup /app
USER appuser
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
