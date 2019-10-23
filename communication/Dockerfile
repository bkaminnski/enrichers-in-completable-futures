FROM bkaminnski/spring-boot-service:11

ARG APPLICATION_PORT
ARG JMX_PORT
ARG DEBUG_PORT

ADD ./target/communication.jar app.jar
EXPOSE $APPLICATION_PORT $JMX_PORT $DEBUG_PORT