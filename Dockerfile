# ---------- Build stage ----------
FROM gradle:8.10-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon -x test

# ---------- Run stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

CMD ["bash", "-lc", "echo '==RUN STAGE==' && java -version && ls -al /app && echo \"PORT=$PORT\" && java -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
