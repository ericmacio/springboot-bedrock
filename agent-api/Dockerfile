FROM eclipse-temurin
COPY ./target/agent-api-0.0.1-SNAPSHOT.jar /usr/src/agent-api/
WORKDIR /usr/src/agent-api
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY
ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
EXPOSE 8080
CMD ["java", "-jar", "chat_request-0.0.1-SNAPSHOT.jar"]