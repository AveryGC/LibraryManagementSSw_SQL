package com.SS.training.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectUtil {
    private String driver= "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost:3306/library";
    private String username = "root";
    private String password = "password";
    private Connection conn =null;
    private static ConnectUtil instance;

    private ConnectUtil() throws SQLException, ClassNotFoundException {
        Class.forName(driver);
        conn = DriverManager.getConnection(url,username,password);
        conn.setAutoCommit(false);
    }

    public static ConnectUtil getInstance() throws SQLException, ClassNotFoundException {
        if(instance==null){
            instance = new ConnectUtil();
        }
        return instance;
    }

    public Connection getConnection() {
            return conn;
    }

    public static void deConsruct() throws SQLException {
        if(instance!=null) {
            instance.conn.close();
            instance = null;
        }
    }
}

