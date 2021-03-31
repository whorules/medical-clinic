FROM maven:3.6.3-jdk-11 AS build
COPY pom.xml /usr/local/service/pom.xml
COPY src /usr/local/service/src
WORKDIR /usr/local/service
RUN mvn clean package

FROM openjdk:11

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV JAVA_OPTS ""

ARG JAR_FILE
COPY target/${JAR_FILE} /opt/medical-clinic/medical-clinic.jar

CMD java $JAVA_OPTS -jar /usr/local/service/${JAR_FILE}
EXPOSE 8080