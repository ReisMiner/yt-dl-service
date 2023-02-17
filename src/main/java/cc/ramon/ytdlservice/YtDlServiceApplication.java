package cc.ramon.ytdlservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "cc.ramon.ytdlservice.repositories")
@SpringBootApplication()
public class YtDlServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(YtDlServiceApplication.class, args);
    }

}
