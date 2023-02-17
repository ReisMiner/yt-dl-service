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
    private String filePath;
    private int length;
    private boolean audioOnly;

    public Video() {

    }

    public Video(String url, String title, String filePath, int length, boolean audioOnly) {
        this.url = url;
        this.title = title;
        this.filePath = filePath;
        this.length = length;
        this.audioOnly = audioOnly;
    }

    public Video(Video v) {
        this.url = v.url;
        this.title = v.title;
        this.filePath = v.filePath;
        this.length = v.length;
        this.audioOnly = v.audioOnly;
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

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", filePath='" + filePath + '\'' +
                ", length=" + length +
                ", audioOnly=" + audioOnly +
                '}';
    }

}
