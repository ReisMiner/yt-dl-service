package cc.ramon.ytdlservice.Runner;

import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;

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
        System.out.println("Started new Video Download: " + video);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Video updatedVid = videoRepository.getReferenceById(video.getId());
        updatedVid.setInQueue(false);
        updatedVid.setFilePath("/video/new/path.mp4");
        videoRepository.save(updatedVid);
    }
}
