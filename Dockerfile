# ---------- Build stage ----------
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
# gradlew 실행권한 부여 후 JAR 빌드 (테스트는 스킵)
RUN chmod +x gradlew && ./gradlew bootJar -x test

# ---------- Run stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app
# build 단계에서 만든 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

# Render가 할당하는 포트를 앱이 사용하도록
ENV PORT=8080
EXPOSE 8080

# Spring Boot의 server.port를 Render의 $PORT로 바인딩
CMD ["bash", "-lc", "java -Dserver.port=${PORT} -jar /app/app.jar"]
