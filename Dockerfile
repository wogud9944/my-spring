# -------- build stage --------
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# -------- run stage --------
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV SPRING_PROFILES_ACTIVE=prod
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
CMD ["sh","-c","java $JAVA_OPTS -jar app.jar"]
