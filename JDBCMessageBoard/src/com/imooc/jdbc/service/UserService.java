package com.imooc.jdbc.service;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.dao.UserDao;

/**
 * UserService
 */
public class UserService {

    private UserDao userDao;

    public UserService(){
        userDao = new UserDao();
    }

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 成功返回用户bean，失败返回null
     */
    public User login(String username, String password){
        return userDao.login(username, password);
    }

    /**
     * 根据Id查询用户信息
     * @param id
     * @return
     */
    public User getUserById(Long id){
        return userDao.getUserById(id);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    public boolean updateUser(User user){
        return userDao.updateUser(user);
    }

    /**
     * 根据用户名查询是否存在用户
     * @param username
     * @return
     */
    public boolean getUserByUsernameAndPassword(String username){
        return userDao.getUserByUsernameAndPassword(username);
    }

    /**
     * 添加用户信息
     * @param username
     * @param password
     * @return
     */
    public boolean addUser(String username, String password){

        return userDao.addUser(username, password);
    }
}
