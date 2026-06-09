# Step 1: Build the Vaadin application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
# Run a clean package compiling the production-optimized frontend assets
RUN mvn clean package -Pproduction -DskipTests

# Step 2: Run the compiled JAR in a lightweight runtime environment
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar

# Render injects its own PORT variable, which we pass directly to the JVM execution
ENV PORT=10000
EXPOSE 10000

ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app.jar"]