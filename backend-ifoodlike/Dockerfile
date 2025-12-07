# Build stage
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Instalar curl para health check
RUN apk add --no-cache curl

# Criar usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar jar
COPY --from=build /app/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Configurar JVM
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Entrypoint
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
