package cc.ramon.ytdlservice.Runner;

import cc.ramon.ytdlservice.models.Video;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

@Service
public class Downloader {

    private final BlockingQueue<Runnable> videoQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executorService =
            new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
                    videoQueue);

    public ExecutorService getExecutor() {
        return executorService;
    }


}

