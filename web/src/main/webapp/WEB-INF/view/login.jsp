<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 10.04.2023
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container text-center">
    <div class="row">
        <div class="col">

        </div>
        <div class="col">
            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="container-lg">
                    <div class="mb-3">
                        <label for="usernameId" class="form-label">Email address</label>
                        <input type="email" class="form-control" id="usernameId" name="username" aria-describedby="emailHelp">
                        <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                    </div>
                    <div class="mb-3">
                        <label for="passwordId" class="form-label">Password</label>
                        <input type="password" class="form-control" id="passwordId" name="password">
                    </div>
                    <button type="submit" class="btn btn-primary">Login</button>
                </div>
            </form>

            <%--<form action="${pageContext.request.contextPath}/login" method="post">--%>
            <%--    <label for="usernameId">Email:</label><br>--%>
            <%--    <input type="email" id="usernameId" name="username"><br>--%>

            <%--    <label for="passwordId">Password:</label><br>--%>
            <%--    <input type="password" id="passwordId" name="password"><br><br>--%>

            <%--    <input type="submit" value="Login">--%>
            <%--</form>--%>

            <form action="${pageContext.request.contextPath}/registration" method="get">
                <input type="submit" value="Регистрация">
            </form>

            <c:if test="${ param.error != null}">
                Неправильный email или пароль
            </c:if>
        </div>
        <div class="col">

        </div>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>
