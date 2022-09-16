package Service;

import DAO.MessageBoardDAO;
import Model.*;
import Util.*;
import java.util.ArrayList;

public class MessageBoardService {


    MessageBoardDAO database;


    public MessageBoardService(MessageBoardDAO database) {
        this.database = database;
    }

    public boolean attemptLogin(User user) {
        User retrievedData = database.getUserByUsername(user.getUsername());
        if (retrievedData == null) return false;
        if (PasswordHelper.saltAndHash(user.getPassword(), retrievedData.getSalt()).equals(retrievedData.getPassword())) return true;
        return false;
    }

    public User getUserByUsername(String username) {
        User requested = database.getUserByUsername(username);
        if (requested == null) return null;
        requested.setPassword("*****");
        requested.setSalt("*****");
        return requested;
    }

    public User getUserById(int id) {
        User requested = database.getUserById(id);
        if (requested == null) return null;
        requested.setPassword("*****");
        requested.setSalt("*****");
        return requested;
    }

    public boolean registerUser(User user) {
        if (database.getUserByUsername(user.getUsername()) != null) return false;
        String salt = PasswordHelper.generateSalt();
        String hashed_password = PasswordHelper.saltAndHash(user.getPassword(),salt);
        return database.addUser(user.getUsername(), hashed_password, salt);
    }

    public ArrayList<SearchResult> searchPostsByRecent(){
        return database.searchRecentPosts();
    }

    public ArrayList<SearchResult> searchPostsByUserId(int id) {
        return database.searchMyPosts(id);
    }

    public ArrayList<SearchResult> searchPostsByTopic(String topic) {
        return database.searchPostsByTopic(topic);
    }

    public ArrayList<SearchResult> searchPostsByTitle(String substring) {
        return database.searchPostsByTitle(substring);
    }

    public Post getPostBySearchResult(SearchResult searchResult){ return null; }

    public Post getPostById(int id){
        database.incrementPostCountById(id);
        return database.getPost(id);
    }

    public void addPost(Post post) {
        database.addPost(post.getTitle(), post.getTopic(),post.getBody(), post.getUsername());
    }

    public void updatePost(int id, Post newPost) {
        database.updatePost(id, newPost.getTitle(), newPost.getBody());
    }

    public void deletePost(int id) {
        database.deletePost(id);
    }

    public ArrayList<Comment> getCommentsByPostId(int id) {
        return database.getCommentsByPostId(id);
    }

    public void addComment(Comment comment) {
        database.addComment(comment.getPostId(),comment.getUsername(), comment.getBody());
    }

    public void deleteComment(int id) {
        database.deleteComment(id);
    }


}
