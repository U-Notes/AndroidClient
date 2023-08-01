package com.example.unotes.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * jdbc链接工具
 *
 * @author witer
 * @date 2023/07/30
 */
public class JdbcUtils {

    private static JdbcUtils instance;

    public static JdbcUtils getInstance() {
        if(instance == null){
            instance = new JdbcUtils();
        }
        return instance;
    }


    public static Connection getConnection(){
         String root = "root";
         String password= "witer330";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://192.168.0.101:3306/" +
                            "unote?allowMultiQueries=true" +
                            "&useUnicode=true" +
                            "&characterEncoding=UTF-8" +
                            "&useSSL=false" +
                            "&allowPublicKeyRetrieval=true",
                    root,
                    password);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn){
        try{
            conn.close();
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }
}