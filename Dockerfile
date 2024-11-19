FROM maven:3.8.5-openjdk-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21.0.1-jdk-slim
COPY --from=build /target/recipes-0.0.1-SNAPSHOT.jar recipes.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "recipes.jar"] 



# FROM maven:4.0.0-openjdk-21 AS build
# COPY . .
# RUN mvn clean package -DskipTests

# FROM openjdk:21.0.1-jdk-slim
# COPY --from=build /target/recipes-0.0.1-SNAPSHOT.jar recipes.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "recipes.jar"]



# FROM maven:3.8.1-openjdk-17-slim
# VOLUME /tmp
# COPY .env .env
# COPY pom.xml pom.xml
# COPY src src
# RUN mvn clean package
# CMD ["java","-jar","/target/Golfskor-2.0.0.jar"]



# FROM maven:3.8.5-openjdk-17 AS build
# COPY . .
# RUN mvn clean package -DskipTests

# FROM openjdk:17.0.1-jdk-slim
# COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "demo.jar"]