package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class RegistServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        if (Objects.equals(password1, password2)){
            boolean result = userService.getUserByUsernameAndPassword(username);
            if (result){
                request.setAttribute("msgUsername", "该用户名已经注册，请重新注册");
                request.getRequestDispatcher("/regPrompt.do").forward(request, response);
            }else{
                if(userService.addUser(username, password1)){
                    request.setAttribute("username", username);
                    request.getRequestDispatcher("/login.do").forward(request,response);
                }else{
                    request.getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(request,response);
                }
            }
        }else{
            request.setAttribute("msg", "俩次输入的密码不一样，请重新输入");
            request.getRequestDispatcher("/regPrompt.do").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        userService = null;
    }
}
