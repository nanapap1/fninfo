FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml ./
RUN mvn -B -f ./pom.xml install -DskipTests
COPY common/ ./common/
RUN mvn -B -f common/pom.xml install -DskipTests
COPY services/info-service/ ./services/info-service/
WORKDIR /build/services/info-service
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /build/services/info-service/target/*.jar app.jar
EXPOSE 8087
ENTRYPOINT ["java", "-jar", "app.jar"]