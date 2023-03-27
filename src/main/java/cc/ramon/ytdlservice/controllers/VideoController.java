package cc.ramon.ytdlservice.controllers;

import cc.ramon.ytdlservice.Runner.Downloader;
import cc.ramon.ytdlservice.Runner.QueueTaskFactory;
import cc.ramon.ytdlservice.models.Video;
import cc.ramon.ytdlservice.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @CrossOrigin
    @PostMapping(base + "/queue")
    public Video queueVideo(@RequestBody Video body) {
        List<Video> exists = videoRepository.findAllByUrl(body.getUrl());
        if (exists.size() != 0) {
            if (exists.size() == 2) return null;
            if (exists.get(0).isAudioOnly() == body.isAudioOnly())
                return null;
        }

        if (body.getUrl().contains("&"))
            body.setUrl(body.getUrl().split("&")[0]);

        body.setInQueue(true);
        body.setTitle("In Queue: " + body.getUrl());
        Video savedVid = videoRepository.save(body);

        downloader.getExecutor().execute(queueTaskFactory.newQueueTask(savedVid));
        return savedVid;
    }

    @CrossOrigin
    @GetMapping(base + "/all")
    public List<Video> allVideos() {
        return videoRepository.findAll();
    }

    @CrossOrigin
    @GetMapping(path = base + "/download")
    public ResponseEntity<Resource> download(@RequestParam int id) {

        try {
            Video video = videoRepository.getReferenceById(id);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + video.getTitle() + "\"");
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");


            Path path = Paths.get(video.getFilePath());
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.getByteArray().length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            e.printStackTrace();
            ByteArrayResource error = new ByteArrayResource("File Not found! Check the ID.".getBytes());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}
