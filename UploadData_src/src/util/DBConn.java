package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConn {

    public static Connection getConnection(String[] objConn) {
//        String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//        String IP = "localhost";
//        String instanceName = "MSSQLSERVER";
//        String port = "1433";
//        String database = "phongkham";
//        String userID = "sa";
//        String password = "12345";
//
//        String url = "jdbc:sqlserver://" + IP + "\\" + instanceName + ":" + port
//                + ";databaseName=" + database + ";user=" + userID + ";password=" + m;

        String IP = objConn[0];
        String port = objConn[1];
        String database = objConn[2];
        String userID = objConn[3];
        String password = objConn[4];

        String url = "jdbc:mysql://" + IP + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, userID, password);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConn.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
