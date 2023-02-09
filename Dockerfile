FROM gradle:jdk17

COPY . /project
RUN  cd /project && gradle build

#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/project/build/libs/yt-dl-service-0.0.1-SNAPSHOT.jar"]