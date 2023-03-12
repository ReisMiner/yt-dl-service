package cc.ramon.ytdlservice.Runner;

import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueTaskFactory {
    @Autowired
    private VideoRepository videoRepository;
    @Value("${video.location}")
    String saveLocation;

    @Transactional
    public QueueTask newQueueTask(Video video) {

        return new QueueTask(video, videoRepository, saveLocation);
    }
}
