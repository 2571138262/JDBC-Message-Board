package com.imooc.jdbc.service;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.dao.MessageDao;

import java.util.Date;
import java.util.List;

/**
 *
 * 消息的service
 */
public class MessageService {

    private MessageDao messageDao;

    public MessageService(){
        messageDao = new MessageDao();
    }

    public boolean addMessage(Message message){
        message.setCreateTime(new Date());
        return messageDao.addMessage(message);
    }

    /**
     * 分页查询全部留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMessage(int page, int pageSize){
        return messageDao.getMessage(page, pageSize);
    }

    /**
     * 分页查询当前用户的留言
     * @param page 当前页码
     * @param pageSize 每页记录数
     * @return
     */
    public List<Message> getMyMessage(int page, int pageSize, String username){
        return messageDao.getMyMessage(page, pageSize, username);
    }

    /**
     * 计算所有留言数量
     * @return
     */
    public int countMessages(){
        return messageDao.countMessages();
    }

    /**
     * 计算当前用户留言数量
     * @return
     */
    public int countMyMessages(String username){
        return messageDao.countMyMessages(username);
    }
}
