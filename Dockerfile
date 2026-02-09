FROM eclipse-temurin:21.0.10_7-jre-alpine

WORKDIR /app

COPY target/*.jar courier-tracking-system.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "courier-tracking-system.jar"]