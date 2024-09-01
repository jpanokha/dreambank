FROM gradle:7.6-jdk17-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM eclipse-temurin:21
# Set the working directory in the container
WORKDIR /app
# Copy the built JAR file from the previous stage to the container
COPY --from=build /home/gradle/src/build/libs/*.jar .
# Set the command to run the application
EXPOSE 7071
ENTRYPOINT ["java", "-Xmx1024M", "-jar", "bank-config-server-0.0.1-SNAPSHOT.jar"]