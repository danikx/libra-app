# # First stage: Download dependencies
# FROM eclipse-temurin:17-jdk-alpine AS deps
# WORKDIR /app
# COPY pom.xml mvnw ./
# COPY .mvn .mvn
# # Create .m2 directory and cache it
# # RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline
# RUN ./mvnw dependency:resolve && ./mvnw dependency:resolve-plugins

# # Second stage: Build
# FROM deps AS build
# COPY src src
# RUN ./mvnw package -DskipTests

# Final stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]