<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 04.05.2023
  Time: 06:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Изменение пользователя</title>
</head>
<body>
<%@ include file="header.jsp" %>

<h2>Имя: ${user.name}</h2>
<h2>Фамилия: ${user.surname}</h2>
<h2>email: ${user.email}</h2>
<h2>Роль: ${user.role}</h2>

<form action="${pageContext.request.contextPath}/user-edit" method="post">
    <label for="emailId">Email:</label><br>
    <input type="email" id="emailId" name="email"><br>

    <label for="passwordId">Пароль:</label><br>
    <input type="password" id="passwordId" name="password"><br><br>

    <label for="nameId">Имя:</label><br>
    <input type="text" id="nameId" name="name"><br>

    <label for="surnameId">Фамилия:</label><br>
    <input type="text" id="surnameId" name="surname"><br>

    <label for="roleId">Роль:</label><br>
    <select name="role" id="roleId">
        <option value="ADMIN">ADMIN</option>
        <option value="MANAGER">MANAGER</option>
        <option value="USER">USER</option>
    </select>

    <input type="hidden" name="id" value="${user.id}">
    <input type="submit" value="Изменить">

</form>

<c:if test="${ param.error == true}">
    Пользователь не изменен
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>