package databaseconnect;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Huy Trung
 */
public class JDBCConnect {
    public static Connection getJDBCConnect(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://TRUNG:1433;databaseName=giaphadb;trustServerCertificate=true";
            String userName= "sa";
            String password= "123456789" ;
            return DriverManager.getConnection(url, userName, password);
            
            
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

