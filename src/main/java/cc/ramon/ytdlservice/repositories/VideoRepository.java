package cc.ramon.ytdlservice.repositories;

import cc.ramon.ytdlservice.models.Video;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    Video findByTitle(String name);
    List<Video> findAllByUrl(String url);

    @Transactional
    void deleteVideoById(Integer id);

    List<Video> findAll();
}
