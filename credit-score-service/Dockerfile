# Use an official Maven image as the base image
FROM maven:3.9-eclipse-temurin-21 AS build
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests
# Use an official OpenJDK image as the base image
FROM eclipse-temurin:21
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /app/target/credit-score-service-1.0.0-SNAPSHOT.jar .
# Set the command to run the application

EXPOSE 9091
EXPOSE 9092
EXPOSE 9093
ENTRYPOINT ["java", "-Xmx1024M", "-jar", "credit-score-service-1.0.0-SNAPSHOT.jar"]