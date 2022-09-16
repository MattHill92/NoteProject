package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class Post {

    private int id = 0;
    private String title;
    private String body;
    private Date date = new Date();
    private int view_count = 0;
    private String topic;
    private String username;
    
    public Post(){};

    public Post(int id, String title, String body, Date date, int view_count, String topic, String username) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.date = date;
        this.view_count = view_count;
        this.topic = topic;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
