FROM eclipse-temurin:17-jdk-alpine as dependencies

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x ./mvnw

# Download dependencies only
RUN ./mvnw dependency:go-offline

FROM eclipse-temurin:17-jdk-alpine as build

WORKDIR /workspace/app

# Copy dependencies from previous stage
COPY --from=dependencies /root/.m2 /root/.m2
COPY --from=dependencies /workspace/app/mvnw ./mvnw
COPY --from=dependencies /workspace/app/.mvn ./.mvn

COPY pom.xml .
COPY src src

RUN ./mvnw package
# RUN ./mvnw package -DskipTests

FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]