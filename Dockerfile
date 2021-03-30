FROM openjdk:11

ENV TZ=UTC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV JAVA_OPTS ""

ARG JAR_FILE
COPY target/${JAR_FILE} /opt/medical-clinic/medical-clinic.jar

CMD java $JAVA_OPTS -jar /opt/medical-clinic/medical-clinic.jar
EXPOSE 8080