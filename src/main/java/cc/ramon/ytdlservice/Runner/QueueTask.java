package cc.ramon.ytdlservice.Runner;

import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
            ArrayList<String> command = new ArrayList<>();
            command.add("youtube-dl");
            command.add("-o");
            command.add("%(channel)s - %(title)s.%(ext)s");
            if (video.isAudioOnly()) {
                command.add("--extract-audio");
                command.add("--audio-format");
                command.add("mp3");
            }

            //TODO: custom video quality
            //if(video.getQuality() != 0)

            command.add(video.getUrl());
            Process process = runtime.exec(command.toArray(new String[0]));
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = lineReader.readLine()) != null)
                logger.info(s);


            command.clear();
            command.add("youtube-dl");
            command.add("--print");
            //use < as separator cuz yt does not allow that in titles
            command.add("%(channel)s<%(duration)s<%(title)s");
            command.add(video.getUrl());

            process = runtime.exec(command.toArray(new String[0]));
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
