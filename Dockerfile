FROM eclipse-temurin:21-jdk-alpine as builder

WORKDIR /opt

COPY . .

RUN ./gradlew clean build --no-daemon && \
    rm -rf /root/.gradle/caches/

FROM eclipse-temurin:21-jre-alpine

RUN adduser -D -H -u 1000 -s /bin/bash appuser

ENV ARTIFACT_NAME=app.jar

WORKDIR /opt/app

COPY --from=builder /opt/build/libs/*.jar /opt/app/$ARTIFACT_NAME

RUN chown -R appuser:appuser /opt/*

USER appuser

EXPOSE 8080

CMD ["sh", "-c",  "java ${JAVA_PARAM} -jar /opt/app/${ARTIFACT_NAME}"]
