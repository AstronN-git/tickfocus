FROM gradle:jdk17 AS cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

FROM gradle:jdk17 AS build
LABEL authors="maximscherbakov"
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
WORKDIR /app
COPY . .
RUN gradle bootJar --no-daemon

FROM eclipse-temurin:17-jre AS run
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]