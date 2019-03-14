package com.imooc.jdbc.dao;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.common.ConnectionUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * UserDao
 */
public class UserDao {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 成功返回用户bean，失败返回null
     */
    public User login(String username, String password){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL
        String sql = "select * from user where username = ? and password = ?";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;
        try{
            // 预编译SQL
            pstmt = conn.prepareStatement(sql);
            // 给参数赋值
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            // 执行SQL语句
            rs = pstmt.executeQuery();
            while (rs.next()){
                user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRealName(rs.getString("real_name"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
            }
        }catch(Exception e){
            System.out.println("登录失败");
            e.printStackTrace();
        }finally {
            //释放资源
            ConnectionUtil.release(rs, pstmt, conn);
        }
        return user;
    }

    /**
     * 根据Id查询用户信息
     * @param id
     * @return
     */
    public User getUserById(Long id){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL
        String sql = "select * from user where id = ?";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        User user = null;
        try{
            // 预编译SQL
            pstmt = conn.prepareStatement(sql);
            // 给参数赋值
            pstmt.setLong(1, id);
            // 执行SQL
            resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRealName(resultSet.getString("real_name"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("查询失败");
        }finally {
            ConnectionUtil.release(resultSet, pstmt, conn);
        }
        return user;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL语句
        String sql = "update user set username = ?, password = ?,real_name = ?, birthday = ?, phone = ?, address = ? where id = ?";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        boolean flag = false;
        try{
            // 预编译SQL语句
            pstmt = conn.prepareStatement(sql);
            // 给参数赋值
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRealName());
            pstmt.setDate(4,  new Date(user.getBirthday().getTime()));
            pstmt.setString(5, user.getPhone());
            pstmt.setString(6, user.getAddress());
            pstmt.setLong(7, user.getId());
            // 执行SQL
            int i = pstmt.executeUpdate();
            if (i > 0){
                flag = true;
            }else{
                flag = false;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("更新用户信息失败");
        }finally {
            ConnectionUtil.release(resultSet, pstmt, conn);
        }
        return flag;
    }

    /**
     * 根据用户名查询是否存在用户
     * @param username
     * @return
     */
    public boolean getUserByUsernameAndPassword(String username){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL
        String sql = "select id from user where username = ? ";
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        boolean result = false;
        try{
            // 预编译SQL
            pstmt = conn.prepareStatement(sql);
            // 给参数赋值
            pstmt.setString(1, username);
            // 执行sql
            resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                if (id != null)
                    result = true;
                else
                    result = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放资源
            ConnectionUtil.release(resultSet, pstmt, conn);
        }
        return result;
    }

    /**
     * 添加用户信息
     * @param username
     * @param password
     * @return
     */
    public boolean addUser(String username, String password){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL
        String sql = "insert user (username, password) values (?, ?)";
        PreparedStatement pstmt = null;
        boolean flag = false;
        try{
            // 预编译SQL
            pstmt = conn.prepareStatement(sql);
            // 给参数赋值
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            // 执行SQL
            int i = pstmt.executeUpdate();
            if(i > 0){
                flag = true;
            }else{
                flag = false;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("添加用户成功");
        }finally {
            ConnectionUtil.release(null, pstmt, conn);
        }
        return flag;
    }
}
