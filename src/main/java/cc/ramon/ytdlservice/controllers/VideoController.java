package cc.ramon.ytdlservice.controllers;

import cc.ramon.ytdlservice.Runner.Downloader;
import cc.ramon.ytdlservice.Runner.QueueTaskFactory;
import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Configurable
public class VideoController {
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private Downloader downloader;
    private final String base = "/video";
    @Autowired
    private QueueTaskFactory queueTaskFactory;

    @PostMapping(base + "/queue")
    public Video queueVideo(@RequestBody Video body) {
        body.setInQueue(true);
        Video savedVid = videoRepository.save(body);

        downloader.getExecutor().execute(queueTaskFactory.newQueueTask(savedVid));
        return savedVid;
    }

    @GetMapping(base + "/all")
    public List<Video> allVideos() {
        return videoRepository.findAll();
    }

}
