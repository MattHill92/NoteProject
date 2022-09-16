package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static Connection conn;
    public static Connection getConnection(){
        if (conn == null)
            try{
                String url = "jdbc:sqlserver://messageboardtestdb.database.windows.net:1433;database=db1;user=CloudSA888c1aa5@messageboardtestdb;password=P@ssword123;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

                conn = DriverManager.getConnection(url);
            }catch(SQLException e){
                e.printStackTrace();
            }
        return conn;
    }
}
