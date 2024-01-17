# syntax = docker/dockerfile:1.2
FROM eclipse-temurin:17.0.8.1_1-jre-jammy

EXPOSE 8080
VOLUME /tmp
ARG GRAFANA_AGENT_FILE
ARG JAR_FILE
COPY ${GRAFANA_AGENT_FILE} opentelemetry-javaagent.jar
COPY ${JAR_FILE} spring-boot.jar
CMD ["java", \
     "-jar", \
     "/spring-boot.jar"]
