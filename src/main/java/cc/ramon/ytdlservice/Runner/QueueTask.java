package cc.ramon.ytdlservice.Runner;

import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueueTask implements Runnable {
    public void setVideo(Video video) {
        this.video = video;
    }

    public QueueTask(Video video, VideoRepository vr) {
        this.videoRepository = vr;
        this.video = video;
    }

    private Video video;
    private VideoRepository videoRepository;

    @Override
    public void run() {
        //TODO: Actual video URL
        //TODO: custom fileformat (mp4 &mp3)
        //TODO: custom video quality
        //TODO: Define download path
        System.out.println("Started new Video Download: " + video);
        Video updatedVid = videoRepository.getReferenceById(video.getId());
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("youtube-dl --extract-audio --audio-format mp3 https://youtube.com/watch?v=0JyCZpCZ6NA");
            process.waitFor();

            process = runtime.exec("youtube-dl --get-filename https://youtube.com/watch?v=0JyCZpCZ6NA");
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            updatedVid.setFilePath(lineReader.lines().findFirst().get());

        } catch (Exception ignore) {
            updatedVid.setFilePath(null);
        }
        updatedVid.setInQueue(false);
        videoRepository.save(updatedVid);
    }
}
