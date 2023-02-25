package cc.ramon.ytdlservice;

import cc.ramon.ytdlservice.Runner.Downloader;
import cc.ramon.ytdlservice.models.Video;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public Video video() {
        return new Video();
    }
}
