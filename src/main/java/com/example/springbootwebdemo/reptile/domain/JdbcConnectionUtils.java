package com.example.springbootwebdemo.reptile.domain;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnectionUtils {
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://localhost:3306/testdb";
    private static String user="root";
    private static String password="123456";
    private JdbcConnectionUtils(){
    }
    public static Connection getConnection(){
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
