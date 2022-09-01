import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    //Real Database
    Connection conn;

    public DatabaseManager(){
        conn = ConnectionUtil.getConnection();
    }

    public boolean checkUsernameExists(String username){
        try{
            PreparedStatement statement = conn.prepareStatement("select * from users where username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()) return true; //checks to see if there is at least 1 row
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String getUserSalt(String username){
        try{
            PreparedStatement statement = conn.prepareStatement("select salt from users where username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("salt");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getUserPassword(String username){
        try{
            PreparedStatement statement = conn.prepareStatement("select password from users where username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("password");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public NoteSession getUserNoteSession(String username){
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> bodies = new ArrayList<>();
        try{
            PreparedStatement statement = conn.prepareStatement("select title, body from notes inner join users on users.username = notes.username where notes.username=?;");
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                titles.add(rs.getString("title"));
                bodies.add(rs.getString("body"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        NoteSession ns = new NoteSession(username, titles.toArray(new String[titles.size()]), bodies.toArray(new String[bodies.size()]), this);
        return ns;
    }

    public NoteSession addUser(String username, String password, String salt){
        try{
            PreparedStatement statement = conn.prepareStatement("insert into users(username, password, salt) values (?, ?, ?);");
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, salt);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return getUserNoteSession(username);
    }

    public void addNote(String username, String name, String body){
        try{
            PreparedStatement statement = conn.prepareStatement("insert into notes(title, body, username) values (?, ?, ?);");
            statement.setString(1, name);
            statement.setString(2, body);
            statement.setString(3,username);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void renameNote(String username, String oldName, String newName){
        try{
            PreparedStatement statement = conn.prepareStatement("update notes set title=? where username=? and title=?;");
            statement.setString(1, newName);
            statement.setString(2, username);
            statement.setString(3,oldName);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void removeNote(String username, String name){
        try{
            PreparedStatement statement = conn.prepareStatement("delete from notes where username=? and title=?;");
            statement.setString(1, username);
            statement.setString(2, name);
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

}
