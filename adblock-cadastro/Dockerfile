FROM openjdk:17-alpine
VOLUME /tmp
COPY target/adblock-cadastro-0.0.1-SNAPSHOT.jar adblock-cadastro-0.0.1-SNAPSHOT.jar
ENV JAVA_OPTS=""
ENV DB_HOST=postgres
ENV DB_PORT=5433
ENV DB_NAME=adiblock
ENV DB_USERNAME=postgres
ENV DB_PASSWORD=1234
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /adblock-cadastro-0.0.1-SNAPSHOT.jar