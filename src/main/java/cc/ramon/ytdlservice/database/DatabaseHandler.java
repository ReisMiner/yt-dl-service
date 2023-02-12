package cc.ramon.ytdlservice.database;

import cc.ramon.ytdlservice.models.QueueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

public class DatabaseHandler {

    private JdbcTemplate jdbcTemplate;
    private static DatabaseHandler databaseHandlerInstance;

    private DatabaseHandler() {
    }

    public static DatabaseHandler getInstance() {
        if (databaseHandlerInstance == null) {
            databaseHandlerInstance = new DatabaseHandler();
        }
        return databaseHandlerInstance;
    }

    public QueueRequest[] getQueueRequests() {
        List<QueueRequest> queueRequests = jdbcTemplate.query("SELECT * FROM ytdl.videos",
                BeanPropertyRowMapper.newInstance(QueueRequest.class));

        return queueRequests.toArray(new QueueRequest[0]);
    }

    public int setQueueRequest(QueueRequest request) {

        return jdbcTemplate.update("INSERT INTO ytdl.videos (url, title, only_audio) VALUES ('?','?','?')",
                pS -> {
                    pS.setString(1, request.url());
                    pS.setString(2, request.title());
                    pS.setBoolean(3, request.audioOnly());
                });
    }

}
