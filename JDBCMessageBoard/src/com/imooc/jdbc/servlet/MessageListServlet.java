package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 *
 * 消息列表的Servlet
 */
public class MessageListServlet extends HttpServlet {
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        super.init();
        messageService = new MessageService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathName = request.getServletPath();
        String pageStr = request.getParameter("page");// 当前页码
        int page = 1;// 页码默认值是1
        if (null != pageStr && !"".equals(pageStr)){
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<Message> messages = null;
        int count = 0;
        if (Objects.equals(pathName, "/message/list.do")){
            messages = messageService.getMessage(page, 5);// 分页查询全部留言
            count = messageService.countMessages();
        }else if(Objects.equals(pathName, "/my/message/list.do")){
            User user = (User) request.getSession().getAttribute("user");
            messages = messageService.getMyMessage(page, 5, user.getName());// 分页查询全部留言
            count = messageService.countMyMessages(user.getName());
            request.setAttribute("who", "me");
        }


        int last = count % 5 == 0 ? (count / 5) : ((count / 5) + 1);
        request.setAttribute("last", last);
        request.setAttribute("messages", messages);
        request.setAttribute("page", page);

        request.getRequestDispatcher("/WEB-INF/views/biz/message_list.jsp").forward(request,response);


    }

    @Override
    public void destroy() {
        super.destroy();
        messageService = null;
    }

}
