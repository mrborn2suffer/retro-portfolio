# Step 1: Build the Vaadin application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# Set Maven options to limit memory usage during compile/build stage
ENV MAVEN_OPTS="-XX:+UseSerialGC -Xmx1024m"
# Force vaadin production mode execution flag here
RUN mvn clean package -Pproduction -DskipTests -Dvaadin.productionMode=true

# Step 2: Run the compiled JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV PORT=10000
EXPOSE 10000

# Optimize JVM settings for container constraints (Render free tier)
ENTRYPOINT ["java", "-XX:+UseSerialGC", "-XX:MaxRAMPercentage=75.0", "-Dserver.port=${PORT}", "-Dspring.profiles.active=production", "-Dvaadin.productionMode=true", "-jar", "app.jar"]