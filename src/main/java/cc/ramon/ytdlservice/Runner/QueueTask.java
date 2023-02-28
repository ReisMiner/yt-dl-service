package cc.ramon.ytdlservice.Runner;

import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;

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

    @Override
    public void run() {
        //TODO: custom fileformat (mp4 &mp3)
        //TODO: Define download path
        System.out.println("Started new Video Download: " + video);
        Video updatedVid = videoRepository.getReferenceById(video.getId());
        Runtime runtime = Runtime.getRuntime();
        try {
            StringBuilder command = new StringBuilder("youtube-dl ");
            if (video.isAudioOnly())
                command.append("--extract-audio --audio-format mp3 ");

            //TODO: custom video quality
            //if(video.getQuality() != 0)

            command.append(video.getUrl());
            Process process = runtime.exec(command.toString());
            process.waitFor();

            //File name with extension
            process = runtime.exec("youtube-dl --get-filename " + video.getUrl());
            BufferedReader lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String fileName = lineReader.lines().findFirst().get();
            if (video.isAudioOnly())
                fileName = fileName.replace(".webm", ".mp3");
            updatedVid.setFilePath("./" + fileName);

            process = runtime.exec("youtube-dl --get-duration " + video.getUrl());
            lineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            updatedVid.setLength(lineReader.lines().findFirst().get().toString());

        } catch (Exception ignore) {
            updatedVid.setFilePath(null);
        }
        updatedVid.setInQueue(false);
        videoRepository.save(updatedVid);
    }
}
