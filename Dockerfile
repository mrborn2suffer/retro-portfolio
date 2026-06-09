# Step 1: Build the Vaadin application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY . .
# Force vaadin production mode execution flag here
RUN mvn clean package -Pproduction -DskipTests -Dvaadin.productionMode=true

# Step 2: Run the compiled JAR
FROM eclipse-temurin:17-jre-alpine
COPY --from=build /target/*.jar app.jar

ENV PORT=10000
EXPOSE 10000

ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-Dspring.profiles.active=production", "-Dvaadin.productionMode=true", "-jar", "/app.jar"]