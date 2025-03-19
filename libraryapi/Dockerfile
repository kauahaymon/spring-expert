# build
FROM maven:3.8.8-amazoncorretto-21-al2023 AS build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# run
FROM amazoncorretto:21.0.6
WORKDIR /app

COPY --from=build ./build/target/*.jar ./libraryapi.jar

EXPOSE 8080

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''

ENV GOOGLE_CLIENT_ID=''
ENV GOOGLE_CLIENT_SECRET=''

ENV SPRING_PROFILES_ACTIVATE='production'

ENV TZ='America/Manaus'

ENTRYPOINT java -jar libraryapi.jar