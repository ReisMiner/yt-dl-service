FROM gradle:jdk17-jammy

RUN apt update && apt install ffmpeg -y
RUN mkdir /yt-dl && cd /yt-dl && wget https://github.com/yt-dlp/yt-dlp/releases/latest/download/yt-dlp_linux -O ./youtube-dl && chmod 777 ./youtube-dl
ENV PATH="${PATH}:/yt-dl"
COPY . /sources
RUN  cd /sources && gradle build


#run the spring boot application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/sources/build/libs/yt-dl-service-0.0.1-SNAPSHOT.jar"]