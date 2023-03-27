package cc.ramon.ytdlservice.models;

import jakarta.persistence.*;

@Entity
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String url;
    private String title;
    private String channel;
    private String filePath;
    private int length;
    private boolean audioOnly;
    private int quality;
    private boolean isInQueue;

    public Video() {

    }

    public Video(String url, String title, String channel, String filePath, int length, boolean audioOnly, int quality, boolean isInQueue) {
        this.url = url;
        this.title = title;
        this.channel = channel;
        this.filePath = filePath;
        this.length = length;
        this.audioOnly = audioOnly;
        this.quality = quality;
        this.isInQueue = isInQueue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isAudioOnly() {
        return audioOnly;
    }

    public void setAudioOnly(boolean audioOnly) {
        this.audioOnly = audioOnly;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public boolean isInQueue() {
        return isInQueue;
    }

    public void setInQueue(boolean inQueue) {
        isInQueue = inQueue;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", channel='" + channel + '\'' +
                ", filePath='" + filePath + '\'' +
                ", length=" + length +
                ", audioOnly=" + audioOnly +
                ", quality=" + quality +
                ", isInQueue=" + isInQueue +
                '}';
    }
}
