package com.imooc.jdbc.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据库操作的公共类
 */
public final class ConnectionUtil {
    private static final String driverClass;
    private static final String url ;
    private static final String username;
    private static final String password;


    private ConnectionUtil(){}

    static{
        // 加载属性文件并解析
        Properties prop = new Properties();
        // 如何获取属性文件的输入流
        // 通常情况下使用类的加载器的方式来进行获取， 不用FileInputStream， 因为当项目发布了以后路径就不能确定了  
        InputStream is = ConnectionUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driverClass = prop.getProperty("driverClass");
        url = prop.getProperty("url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            System.out.println("找不到驱动程序类，加载驱动失败。");
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("创建数据库连接失败");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放数据库资源
     * @param resultSet
     * @param stmt
     * @param conn
     */
    public static void release(ResultSet resultSet, Statement stmt, Connection conn){
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(stmt != null){
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }finally {
                        if (conn != null){
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            conn = null;
                        }
                    }
                    stmt = null;
                }
            }
            resultSet = null;
        }
    }
}
