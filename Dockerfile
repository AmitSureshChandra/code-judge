FROM maven:3-adoptopenjdk-8 as builder
WORKDIR /opt/app

COPY pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app

ENV RUN_SEEDER=false
ENV USE_SSL=false
ENV DB_USERNAME=root
ENV DB_PASS=mypass
ENV DB_HOST=localhost
ENV DB_PORT=3306
ENV DB_NAME=code_judge
ENV ONLINE_COMPILER_URL="http://127.0.0.1:8010"
ENV CORS_ORIGINS=*
ENV MQ_USERNAME=guest
ENV MQ_PASS=guest
ENV MQ_HOST=localhost
ENV MQ_VHOST=/
ENV MQ_PORT=5672
ENV REDIS_HOST=127.0.0.1
ENV REDIS_PORT=6379
ENV SECRET_TOKEN=my-secret-no-secret

EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar

ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]