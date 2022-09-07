import java.util.Date;

public class SearchResult {

    int post_id;
    String post_title;
    String topic;
    String username;
    Date date;
    int view_count;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
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

    @Override
    public String toString() {
        return "| " + String.format("%-10.10s",topic) + " | " + String.format("%-40.40s", post_title) + " | " + String.format("%-3s",view_count) + " | Posted on " + date +" by " +username;
    }
}
