FROM maven:3.6.3-jdk-11 AS build
COPY pom.xml /opt/user-service/pom.xml
COPY src /opt/user-service/src
WORKDIR /opt/user-service
RUN mvn clean package

FROM openjdk:11

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV JAVA_OPTS ""

COPY target/medical-clinic.jar /opt/medical-clinic/medical-clinic.jar

CMD java $JAVA_OPTS -jar /opt/user-service/medical-clinic.jar
EXPOSE 8080