package cc.ramon.ytdlservice.controllers;

import cc.ramon.ytdlservice.database.DatabaseHandler;
import cc.ramon.ytdlservice.models.QueueRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoController {

    private final String base = "/video";

    @PostMapping(base + "/queue")
    public QueueRequest queueVideo(@RequestBody QueueRequest body) {
        System.out.println(DatabaseHandler.getInstance().setQueueRequest(body));
        return new QueueRequest(body.url(), body.title(), body.audioOnly());
    }

    @GetMapping(base + "/all")
    public QueueRequest[] queueVideo() {
        return DatabaseHandler.getInstance().getQueueRequests();
    }

}
