package cc.ramon.ytdlservice.controllers;

import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VideoController {
    @Autowired
    private VideoRepository videoRepository;
    private final String base = "/video";

    @PostMapping(base + "/queue")
    public Video queueVideo(@RequestBody Video body) {
        videoRepository.save(body);
        return new Video(body);
    }

    @GetMapping(base + "/all")
    public List<Video> allVideos() {
        return videoRepository.findAll();
    }

}
