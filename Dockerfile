# Use an official OpenJDK 17 runtime as a base image
FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app.jar"]