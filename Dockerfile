# Usar a imagem base do Maven para construir a aplicação
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN mvn package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar

FROM openjdk:17-jdk-alpine

WORKDIR /app

ARG JAR_FILE=/app/target/*.jar
COPY --from=builder ${JAR_FILE} application.jar

CMD ["java", "-jar", "application.jar"]