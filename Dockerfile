# ---- Build Stage ----
FROM gradle:8.10-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# ---- Run Stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar

# Render가 PORT 환경변수를 줍니다.
ENV PORT=8080
EXPOSE 8080

CMD ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
