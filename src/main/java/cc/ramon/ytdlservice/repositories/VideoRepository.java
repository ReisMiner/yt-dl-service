package cc.ramon.ytdlservice.repositories;

import cc.ramon.ytdlservice.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findByTitle(String name);

    Video deleteVideoById(Integer name);

    List<Video> findAll();
}
