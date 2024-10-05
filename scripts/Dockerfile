FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY *.jar app.jar
COPY JVM_OPTIONS.sh .

ENV JVM_OPTIONS ""
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh", "-c", "JVM_OPTIONS=$(sh ./JVM_OPTIONS.sh) && java ${JVM_OPTIONS} -jar app.jar"]

EXPOSE 8080