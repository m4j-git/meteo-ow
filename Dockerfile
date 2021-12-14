FROM eclipse-temurin:17

EXPOSE 8081

ENV JAVA_OPTS="-Xms256M -Xmx512M -server -Djava.security.edg=file:/dev/urandom -XX:MaxHeapFreeRatio=70 -XX:MaxGCPauseMillis=10"
ENV TOMCAT_OPTS="-Dserver.tomcat.accesslog.directory=logs -Dserver.tomcat.accesslog.enabled=true -Duser.timezone=Europe/Moscow"

ADD service/target/meteo-ow.jar /opt/meteo/meteo-ow.jar

WORKDIR /opt/meteo
CMD java -jar $JAVA_OPTS $TOMCAT_OPTS meteo-ow.jar

