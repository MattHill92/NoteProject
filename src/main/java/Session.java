import java.util.ArrayList;

public class Session {

    String username;
    DatabaseManager database;

    ArrayList<SearchResult> searchResults = new ArrayList<>();
    Post post;

    public Session(String username, DatabaseManager database) {
        this.username = username;
        this.database = database;
    }

    public void fetchRecentSearchResults(){
        searchResults = database.searchRecentPosts();
    }
    public void fetchMyPostResults(){
        searchResults = database.searchMyPosts(username);
    }
    public void fetchTitleResults(String substring) {
        searchResults = database.searchPostsByTitle(substring);
    }
    public void fetchTopicResults(String topic) {
        searchResults = database.searchPostsByTopic(topic);
    }

    public void requestDeletePostComment(int targetIndex) {
        int commentID = post.getComments().get(targetIndex).getId();
        database.removeComment(commentID);
        refreshPost();
    }

    public void requestLeaveComment(String comment) {
        database.addComment(post.getId(), username, comment);
        refreshPost();
    }

    private void refreshPost() {
        fetchPost(post.getId());
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<SearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(ArrayList<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    public Post fetchPost(int postID) {
        post = database.getPost(postID);
        return post;
    }

    public Post getPost(){
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public void requestUpdatePost(String title, String body) {
        database.updatePost(post.getId(), title, body);
        refreshPost();
    }

    public void requestDeletePost() {
        database.deletePost(post.getId());
        fetchRecentSearchResults();
    }

    public void requestCreatePost(String title, String topic, String body) {
        int newPostId = database.addPost(title, topic, body, username);
        fetchRecentSearchResults();
        fetchPost(newPostId);
    }



}
