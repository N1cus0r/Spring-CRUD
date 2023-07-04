FROM maven:3-eclipse-temurin-17
WORKDIR /SchoolApp
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run