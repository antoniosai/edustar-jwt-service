FROM openjdk:17-jdk-alpine3.14
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY target/*.jar app.jar
EXPOSE 7999
ENTRYPOINT ["java","-jar","app.jar"]
