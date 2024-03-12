# Dockerfile for building a Docker image to run a Java application.
#
# The official OpenJDK 17 JDK as the base image.
FROM openjdk:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
