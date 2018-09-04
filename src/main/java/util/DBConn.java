package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConn {

    public static Connection getConnection(String[] objConn) {

        String IP = objConn[0];
        String port = objConn[1];
        String database = objConn[2];
        String userID = objConn[3];
        String password = objConn[4];

        String url = "jdbc:mysql://" + IP + ":" + port + "/" + database + "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, userID, password);
            return conn;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
