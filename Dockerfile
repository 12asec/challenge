FROM maven:3.9.6-eclipse-temurin-22 AS build
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests
FROM openjdk:21
WORKDIR /app
COPY --from=build /app/target/challenge-1.0-SNAPSHOT.jar .
CMD ["java", "-jar", "challenge-1.0-SNAPSHOT.jar"]