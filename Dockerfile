FROM maven:3.9.4-eclipse-temurin-21-alpine AS build

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
ARG DATABASE_URL="jdbc:postgresql://dpg-cs9r4hi3esus739ko560-a.frankfurt-postgres.render.com:5432/recipe_postgres_ff6l"
ARG DATABASE_USERNAME="recipe_postgres_ff6l_user"
ARG DATABASE_PASSWORD="pwWR34uholI0iszXLMXvMceT8moLZMaM"

ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USERNAME=${DATABASE_USERNAME}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}

COPY --from=build /target/*.jar recipes.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","recipes.jar"]
