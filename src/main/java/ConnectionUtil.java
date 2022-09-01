import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static Connection conn;
    public static Connection getConnection(){
        if (conn == null)
            try{
                String url = "jdbc:sqlserver://localhost:1433;"
                        + "user=sa;"
                        + "password=P@SSWORD123;"
                        + "trustServerCertificate=true;";
                conn = DriverManager.getConnection(url);
            }catch(SQLException e){
                e.printStackTrace();
            }
        return conn;
    }
}
