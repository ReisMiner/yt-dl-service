package cc.ramon.ytdlservice.repositories;

import cc.ramon.ytdlservice.models.Video;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VideoRepository extends CrudRepository<Video, Integer> {
    Video findByTitle(String name);

    Video deleteVideoById(Integer name);

    List<Video> findAll();
}
