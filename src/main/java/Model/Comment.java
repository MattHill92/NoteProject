package Model;

import java.util.Date;

public class Comment {

    int id;
    int postId;
    String body;
    String username;
    Date date;

    public Comment(){}

    public Comment(int id, int postId, String body, String username, Date date) {
        this.id = id;
        this.postId = postId;
        this.body = body;
        this.username = username;
        this.date = date;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
