FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
ARG DATABASE_URL
ARG DATABASE_USERNAME
ARG DATABASE_PASSWORD

ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

COPY --from=build /target/*.jar recipes.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","recipes.jar"]
