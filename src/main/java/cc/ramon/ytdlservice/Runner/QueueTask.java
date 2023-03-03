package cc.ramon.ytdlservice.Runner;

import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueueTask implements Runnable {
    public QueueTask(Video video, VideoRepository vr) {
        this.videoRepository = vr;
        this.video = video;
    }

    private final Video video;
    private final VideoRepository videoRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        //TODO: Define download path
        logger.info("Started new Video Download: " + video);
        Video updatedVid = videoRepository.getReferenceById(video.getId());
        Runtime runtime = Runtime.getRuntime();
        try {
            StringBuilder command = new StringBuilder("youtube-dl -o \"%(channel)s - %(title)s.%(ext)s\" ");
            if (video.isAudioOnly())
                command.append("--extract-audio --audio-format mp3 ");

            //TODO: custom video quality
            //if(video.getQuality() != 0)

            command.append(video.getUrl());
            Process process = runtime.exec(command.toString());
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = lineReader.readLine()) != null)
                logger.info(s);


            //use < as separator cuz yt does not allow that in titles
            process = runtime.exec("youtube-dl --print \"%(channel)s<%(duration)s<%(title)s\" " + video.getUrl());
            lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String[] infos = lineReader.lines().findFirst().get().toString().split("<");

            updatedVid.setChannel(infos[0]);
            updatedVid.setLength(Integer.parseInt(infos[1]));
            updatedVid.setTitle(infos[2]);

            //File name with extension
            String fileName = infos[0] + " - " + infos[2];
            if (video.isAudioOnly())
                fileName += ".mp3";
            else
                fileName += ".webm";
            updatedVid.setFilePath("./" + fileName);
        } catch (Exception ignore) {
            updatedVid.setFilePath(null);
        }
        updatedVid.setInQueue(false);
        logger.info("Download of " + video + " done!");
        videoRepository.save(updatedVid);
    }
}
