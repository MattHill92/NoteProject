package DAO;

import Model.*;
import Util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageBoardDAO {

    //Real Database
    Connection conn;

    public MessageBoardDAO(){
        conn = ConnectionUtil.getConnection();
    }

    public User getUserByUsername(String username){
        try{
            PreparedStatement statement = conn.prepareStatement("select * from users where username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()) return null;
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public User getUserById(int id){
        try{
            PreparedStatement statement = conn.prepareStatement("select * from users where id=?;");
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean addUser(String username, String password, String salt){
        try{
            PreparedStatement statement = conn.prepareStatement("insert into users(username, password, salt) values (?, ?, ?);");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, salt);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public ArrayList<SearchResult> searchRecentPosts() {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement("select posts.id, title, topic, username, view_count, date_posted from posts join users on users.id = posts.user_id order by date_posted desc;");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                SearchResult s = new SearchResult();
                s.setPost_id(rs.getInt("id"));
                s.setPost_title(rs.getString("title"));
                s.setTopic(rs.getString("topic"));
                s.setUsername(rs.getString("username"));
                s.setDate(rs.getDate("date_posted"));
                s.setView_count(rs.getInt("view_count"));
                searchResults.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return searchResults;
    }

    public ArrayList<SearchResult> searchMyPosts(int userId) {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement("select posts.id, title, topic, username, view_count, date_posted from posts join users on users.id = posts.user_id where user_id = ? order by date_posted desc;");
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                SearchResult s = new SearchResult();
                s.setPost_id(rs.getInt("id"));
                s.setPost_title(rs.getString("title"));
                s.setTopic(rs.getString("topic"));
                s.setUsername(rs.getString("username"));
                s.setDate(rs.getDate("date_posted"));
                s.setView_count(rs.getInt("view_count"));
                searchResults.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return searchResults;
    }

    public ArrayList<SearchResult> searchPostsByTopic(String topic) {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement("select posts.id, title, topic, username, view_count, date_posted from posts join users on users.id = posts.user_id where topic = ? order by date_posted desc;");
            statement.setString(1, topic);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                SearchResult s = new SearchResult();
                s.setPost_id(rs.getInt("id"));
                s.setPost_title(rs.getString("title"));
                s.setTopic(rs.getString("topic"));
                s.setUsername(rs.getString("username"));
                s.setDate(rs.getDate("date_posted"));
                s.setView_count(rs.getInt("view_count"));
                searchResults.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return searchResults;
    }

    public ArrayList<SearchResult> searchPostsByTitle(String titleSubstring) {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement("select posts.id, title, topic, username, view_count, date_posted from posts join users on users.id = posts.user_id where title like ? order by date_posted desc;");
            statement.setString(1, "%"+titleSubstring+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                SearchResult s = new SearchResult();
                s.setPost_id(rs.getInt("id"));
                s.setPost_title(rs.getString("title"));
                s.setTopic(rs.getString("topic"));
                s.setUsername(rs.getString("username"));
                s.setDate(rs.getDate("date_posted"));
                s.setView_count(rs.getInt("view_count"));
                searchResults.add(s);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return searchResults;
    }

    public Post getPost(int postID) {
        Post post = new Post();
        try{
            PreparedStatement statement = conn.prepareStatement("select posts.id, title, topic, body, username, view_count, date_posted from posts join users on users.id = posts.user_id where posts.id = ?;");
            statement.setInt(1, postID);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                post.setId(rs.getInt("id"));
                post.setTitle(rs.getString("title"));
                post.setBody(rs.getString("body"));
                post.setTopic(rs.getString("topic"));
                post.setUsername(rs.getString("username"));
                post.setDate(rs.getDate("date_posted"));
                post.setView_count(rs.getInt("view_count"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        return post;
    }

    public void incrementPostCountById(int postId){
        try{
            PreparedStatement statement = conn.prepareStatement("update posts set view_count = view_count + 1 where id = ?;");
            statement.setInt(1, postId);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void updatePost(int id, String title, String body) {
        try{
            PreparedStatement statement = conn.prepareStatement("update posts set title=?, body=? where id=?;");
            statement.setString(1, title);
            statement.setString(2, body);
            statement.setInt(3, id);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deletePost(int postID) {
        try{
            PreparedStatement statement = conn.prepareStatement("delete from comments where post_id=?;");
            statement.setInt(1, postID);
            statement.executeUpdate();

            statement = conn.prepareStatement("delete from posts where id=?;");
            statement.setInt(1, postID);
            statement.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int addPost(String title, String topic, String body, String username) {
        int userID = getUserByUsername(username).getId();
        try{
            PreparedStatement statement = conn.prepareStatement("insert into posts(title, topic, body, user_id) values (?, ?, ?, ?);");
            statement.setString(1, title);
            statement.setString(2, topic);
            statement.setString(3, body);
            statement.setInt(4, userID);
            statement.executeUpdate();

            statement = conn.prepareStatement("select id from posts where user_id=? order by date_posted desc;");
            statement.setInt(1, userID);
            ResultSet rs = statement.executeQuery();

            if (rs.next())
                return rs.getInt("id");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    public ArrayList<Comment> getCommentsByPostId(int postId){
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement("select comments.id, post_id, username, body, date_posted from comments join users on users.id = comments.user_id where post_id = ? order by date_posted asc;");
            statement.setInt(1, postId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Comment c = new Comment();
                c.setPostId(rs.getInt("post_id"));
                c.setId(rs.getInt("id"));
                c.setBody(rs.getString("body"));
                c.setDate(rs.getDate("date_posted"));
                c.setUsername(rs.getString("username"));
                comments.add(c);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return comments;
    }

    public void deleteComment(int postID) {
        try{
            PreparedStatement statement = conn.prepareStatement("delete from comments where id=?;");
            statement.setInt(1, postID);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void addComment(int postID, String username, String comment) {
        int userID = getUserByUsername(username).getId();
        try{
            PreparedStatement statement = conn.prepareStatement("insert into comments(user_id, post_id, body) values (?, ?, ?);");
            statement.setInt(1, userID);
            statement.setInt(2, postID);
            statement.setString(3, comment);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
