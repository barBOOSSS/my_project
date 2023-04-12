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

<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="loginId">Login:</label><br>
    <input type="text" id="loginId" name="login"><br>

    <label for="passwordId">Password:</label><br>
    <input type="password" id="passwordId" name="password"><br><br>

    <input type="submit" value="Submit">
</form>

<c:if test="${ param.error == true}">
Неправильный логин или пароль
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
