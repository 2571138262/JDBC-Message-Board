package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 留言处理Servlet
 */
public class MessageServlet extends HttpServlet {

    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = new MessageService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathName = request.getServletPath();
        if (Objects.equals("/addMessagePrompt.do", pathName)){// 进入编辑留言页面
            request.getRequestDispatcher("/WEB-INF/views/biz/add_message.jsp").forward(request, response);
        }else if(Objects.equals("/addMessage.do", pathName)){// 提交编辑留言
            User user = (User) request.getSession().getAttribute("user");
            if (null == user){
                request.getRequestDispatcher("/message/list.do").forward(request,response);
            }else{
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                boolean result = messageService.addMessage(new Message(0, user.getId(), user.getName(),title,content,null));
                if (result)
                    request.getRequestDispatcher("/message/list.do").forward(request, response);// /WEB-INF/views/biz/message_list.jsp
                else
                    request.getRequestDispatcher("/addMessage.do").forward(request, response);
            }
        }else {
            request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        messageService = null;
    }
}
