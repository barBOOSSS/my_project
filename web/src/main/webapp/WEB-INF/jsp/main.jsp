<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 13.04.2023
  Time: 00:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>==Отель==</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h2><a href=${pageContext.request.contextPath}/?id=1>1 - Регистрация</a></h2>
<h2><a href=${pageContext.request.contextPath}/?id=2>2 - Войти в аккаунт</a></h2>
<h2><a href=${pageContext.request.contextPath}/?id=3>3 - Выход</a></h2>

<%@ include file="footer.jsp" %>
</body>
</html>
