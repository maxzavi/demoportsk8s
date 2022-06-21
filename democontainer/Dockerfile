FROM eclipse-temurin:17-jre-alpine
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
COPY target/democontainer-0.0.1-SNAPSHOT.jar democontainer.jar
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar democontainer.jar
