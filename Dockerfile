FROM openjdk:11-jre-slim
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /home/welcomebot
WORKDIR /home/welcomebot
ADD target/welcome_bot-0.0.1-SNAPSHOT.jar welcome_bot.jar
ENTRYPOINT ["sh", "-c", "java -jar welcome_bot.jar"]
