import DAO.MessageBoardDAO;
import Model.Comment;
import Model.Post;
import Model.SearchResult;
import Model.User;
import Util.PasswordHelper;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;

public class DummyMessageBoardDAO extends MessageBoardDAO {
    public User user = new User(0,"null", PasswordHelper.saltAndHash("null","$2a$10$dgO4ClcULbCPEix.glhA3e"), "$2a$10$dgO4ClcULbCPEix.glhA3e");
    public SearchResult searchResult = new SearchResult(0, "null", "null", "null", null, 0);
    public ArrayList<SearchResult> searchList = new ArrayList<>();
    public Post post = new Post(0, "null", "null", null, 0, "null", "null");
    public Comment comment = new Comment(0,0,"null","null",null);
    public ArrayList<Comment> commentList = new ArrayList<>();
    public boolean methodCalled = false;

    public DummyMessageBoardDAO() {
        super();
        searchList.add(searchResult);
        commentList.add(comment);
    }

    @Override
    public User getUserByUsername(String username) {
        return user;
    }

    @Override
    public User getUserById(int id) {
        return user;
    }

    @Override
    public boolean addUser(String username, String password, String salt) {
        methodCalled = true;
        return true;
    }

    @Override
    public ArrayList<SearchResult> searchRecentPosts() {
        return searchList;
    }

    @Override
    public ArrayList<SearchResult> searchMyPosts(int userId) {
        return searchList;
    }

    @Override
    public ArrayList<SearchResult> searchPostsByTopic(String topic) {
        return searchList;
    }

    @Override
    public ArrayList<SearchResult> searchPostsByTitle(String titleSubstring) {
        return searchList;
    }

    @Override
    public Post getPost(int postID) {
        return post;
    }

    @Override
    public void incrementPostCountById(int postId) {
        methodCalled = true;
    }

    @Override
    public void updatePost(int id, String title, String body) {
        methodCalled = true;
    }

    @Override
    public void deletePost(int postID) {
        methodCalled = true;
    }

    @Override
    public int addPost(String title, String topic, String body, String username) {
        methodCalled = true;
        return 0;
    }

    @Override
    public ArrayList<Comment> getCommentsByPostId(int postId) {
        return commentList;
    }

    @Override
    public void deleteComment(int postID) {
        methodCalled = true;
    }

    @Override
    public void addComment(int postID, String username, String comment) {
        methodCalled = true;
    }
}
