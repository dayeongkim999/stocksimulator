# 1️⃣ 빌드 환경: JDK 17
FROM amazoncorretto:17

# 2️⃣ 작업 디렉토리 설정
WORKDIR /app

# 3️⃣ 빌드된 JAR 파일 복사
#    (springboot/target/ 내부의 jar 파일을 app.jar로 복사)
COPY build/libs/*.jar app.jar

# 4️⃣ 환경 변수 (선택)
#    여기서는 로그나 설정 경로를 나중에 docker-compose.yml에서 지정할 수도 있음
ENV TZ=Asia/Seoul

# 5️⃣ 내부 포트 열기
EXPOSE 8081

# 6️⃣ 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]
