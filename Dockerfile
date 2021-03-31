FROM maven:3.6.3-jdk-11-slim AS build
COPY pom.xml /opt/medical-clinic/pom.xml
COPY src /opt/medical-clinic/src
WORKDIR /opt/medical-clinic
RUN mvn clean package

FROM openjdk:11-jre-slim

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV JAVA_OPTS ""

COPY --from=build /opt/medical-clinic/target/medical-clinic.jar /opt/medical-clinic/medical-clinic.jar

CMD java $JAVA_OPTS -jar /opt/medical-clinic/medical-clinic.jar
EXPOSE 8080