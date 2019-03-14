<%@ page import="com.imooc.jdbc.bean.Message" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>留言板</title>
        <link rel="stylesheet" href="../../../css/index.css">
        <script type="text/javascript">
            function submitMessageForm(flag) {
                if ('first' == flag) {
                    document.getElementById('page').value = 1;
                } else if ('pre' == flag) {
                    var current = Number(document.getElementById('page').value);
                    if (current > 1) {
                        document.getElementById('page').value = current - 1;
                    }
                } else if ('next' == flag) {
                    var current = Number(document.getElementById('page').value);
                    var last = Number(document.getElementById('last').value);
                    if (current < last) {
                        document.getElementById('page').value = current + 1;
                    }
                } else if ('last' == flag) {
                    var last = Number(document.getElementById('last').value);
                    document.getElementById('page').value = last < 1 ? 1 : last;
                }
                document.getElementById('messageForm').submit();
            }
        </script>
    </head>

    <body>
        <header>
            <div class="container">
                <% if (null != request.getSession().getAttribute("user")) {
                    String who = (String) request.getAttribute("who");
                    if ("me".equals(who)) {%>
                        <nav>
                            <a href="/message/list.do">全部留言</a>
                        </nav>
                    <% } else {%>
                        <nav>
                            <a href="/my/message/list.do">我的留言</a>
                        </nav>
                    <% } %>
                        <nav>
                            <a href="/userInfo.do">我的信息</a>
                        </nav>
                <%} else { %>
                    <nav>
                        <a href="/login.do">登录</a>
                        <a href="/regPrompt.do">注册</a>
                    </nav>
                <% } %>
            </div>
        </header>
        <section class="banner">
            <div class="container">
                <div>
                    <h1>慕课网留言板</h1>
                    <p>慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。 </p>
                </div>
            </div>
        </section>
        <section class="main">
            <div class="container">
                <%for (Message m : (List<Message>)request.getAttribute("messages")){%>
                <%--<c:forEach items="${messages}" var="msg">--%>
                    <div class="alt-item">
                        <div class="alt-head">
                            <div class="alt-info">
                                <span>作者：<a href=""><%=m.getUsername()%></a></span>
                                <span>时间：<%=m.getCreateTime()%></span>
                            </div>
                        </div>
                        <div class="alt-content">
                            <h3><%=m.getTitle()%></h3>
                            <p><%=m.getContent()%></p>
                        </div>
                    </div>
                <%--</c:forEach>--%>
                <%}%>
            </div>
        </section>
        <section class="page">
            <div class="container">
                <% if (null != request.getSession().getAttribute("user")) {%>
                    <div id="fatie">
                        <a href="/addMessagePrompt.do"><button>点我留言</button></a>
                    </div>
                <%} else { %>
                    <div id="fatie">
                        请<a href="/login.do"><button>登录</button></a>后留言
                    </div>
                <% } %>


                <div id="pagefy">
                    <ul>
                        <form id="messageForm" action="/message/list.do" method="post">
                            <input type="hidden" id="page" name="page" value="${page}">
                            <input type="hidden" id="last" name="last" value="${last}">
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('first')">首页</a></li>
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('pre')">上一页</a></li>
                            <li><a href="javascript:void(0)">当前第${page}页</a></li>
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('next')">下一页</a></li>
                            <li><a href="javascript:void(0)" onclick="submitMessageForm('last')">尾页</a></li>
                        </form>
                    </ul>
                </div>
            </div>
        </section>
        <footer>
            copy@慕课网
        </footer>
    </body>
</html>