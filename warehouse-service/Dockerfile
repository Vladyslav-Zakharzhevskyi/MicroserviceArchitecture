FROM gradle:8.14.2-jdk24 AS build

WORKDIR /home/gradle/project

COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN gradle --no-daemon clean build -x test

FROM eclipse-temurin:24-jre

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 2222
CMD ["java", "-jar", "app.jar"]