FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build /target/*.jar recipes.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar"]
