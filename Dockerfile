FROM gradle:jdk17 AS build
LABEL authors="maximscherbakov"
WORKDIR /app
COPY . .
RUN gradle bootJar

FROM eclipse-temurin:17-jre AS run
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]