FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml ./
COPY common/ ./common/
COPY services/tg-service/ ./services/tg-service/
WORKDIR /build/services/tg-service
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /build/services/tg-service/target/*.jar app.jar
EXPOSE 8089
ENTRYPOINT ["java", "-jar", "app.jar"]