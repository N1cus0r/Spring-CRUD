#FROM openjdk:17-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM maven:3-eclipse-temurin-17
WORKDIR /SchoolApp
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run