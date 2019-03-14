package com.imooc.jdbc.dao;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.common.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 消息的Dao
 */
public class MessageDao {

    /**
     * 分页查询全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMessage(int page, int pageSize){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL语句
        String sql = "select * from message order by create_time desc limit ?, ?";// limit m, n:从第m条开始取出总共n条记录
        PreparedStatement ptmt = null;
        ResultSet resultSet = null;
        List<Message> messages = new ArrayList<>();
        try{
            // 预编译SQL
            ptmt = conn.prepareStatement(sql);
            // 给参数赋值
            ptmt.setInt(1, (page - 1) * pageSize);
            ptmt.setInt(2, pageSize);
            // 执行SQL
            resultSet = ptmt.executeQuery();
            while (resultSet.next()){
                messages.add(new Message(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getDate("create_time")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 释放资源
            ConnectionUtil.release(resultSet, ptmt, conn);
        }
        return messages;
    }

    /**
     * 分页查询当前用户留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMyMessage(int page, int pageSize, String username){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL语句
        String sql = "select * from message where username = ? order by create_time desc limit ?, ?";// limit m, n:从第m条开始取出总共n条记录
        PreparedStatement ptmt = null;
        ResultSet resultSet = null;
        List<Message> messages = new ArrayList<>();
        try{
            // 预编译SQL
            ptmt = conn.prepareStatement(sql);
            // 给参数赋值
            ptmt.setString(1, username);
            ptmt.setInt(2, (page - 1) * pageSize);
            ptmt.setInt(3, pageSize);
            // 执行SQL
            resultSet = ptmt.executeQuery();
            while (resultSet.next()){
                messages.add(new Message(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getDate("create_time")
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 释放资源
            ConnectionUtil.release(resultSet, ptmt, conn);
        }
        return messages;
    }

    /**
     * 计算所有留言数量
     * @return
     */
    public int countMessages(){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL语句
        String sql = "select count(*) as total from message";
        PreparedStatement ptmt = null;
        ResultSet resultSet = null;
        int result = 0;
        try{
            // 预编译SQL
            ptmt = conn.prepareStatement(sql);
            // 执行SQL
            resultSet = ptmt.executeQuery();
            while (resultSet.next()){
                result = resultSet.getInt("total");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionUtil.release(resultSet, ptmt, conn);
        }
        return result;
    }

    /**
     * 计算所有留言数量
     * @return
     */
    public int countMyMessages(String username){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL语句
        String sql = "select count(*) as total from message where username = ?";
        PreparedStatement ptmt = null;
        ResultSet resultSet = null;
        int result = 0;
        try{
            // 预编译SQL
            ptmt = conn.prepareStatement(sql);
            // 给参数赋值
            ptmt.setString(1, username);
            // 执行SQL
            resultSet = ptmt.executeQuery();
            while (resultSet.next()){
                result = resultSet.getInt("total");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            ConnectionUtil.release(resultSet, ptmt, conn);
        }
        return result;
    }

    /**
     * 添加留言
     * @param message
     * @return
     */
    public boolean addMessage(Message message){
        // 获得连接
        Connection conn = ConnectionUtil.getConnection();
        // 编写SQL
        String sql = "insert message(user_id,username,title,content,create_time) values(?,?,?,?,?)";
        PreparedStatement pstmt = null;
        boolean flag = false;
        try{
            // 预编译SQL
            pstmt = conn.prepareStatement(sql);
            // 给参数赋值
            pstmt.setLong(1, message.getId());
            pstmt.setString(2, message.getUsername());
            pstmt.setString(3, message.getTitle());
            pstmt.setString(4, message.getContent());
            pstmt.setTimestamp(5, new Timestamp(message.getCreateTime().getTime()));
            int i = pstmt.executeUpdate();
            if (i > 0){
                flag = true;
            }else {
                flag = false;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("添加留言不成功");
        }finally {
            ConnectionUtil.release(null, pstmt, conn);
        }
        return flag;
    }
}
