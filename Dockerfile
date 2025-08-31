FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/products-service-0.0.1-SNAPSHOT.jar /app/products-service.jar

ENTRYPOINT ["java", "-jar", "/app/auth-service.jar"]

EXPOSE 8081