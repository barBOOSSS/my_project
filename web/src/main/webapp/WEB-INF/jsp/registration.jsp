<%--
  Created by IntelliJ IDEA.
  User: barboosss
  Date: 12.04.2023
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Регитрация</title>
</head>
<body>
<%@ include file="header.jsp" %>

<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="emailId">Email:</label><br>
    <input type="email" id="emailId" name="email"><br>

    <label for="passwordId">Password:</label><br>
    <input type="text" id="passwordId" name="password"><br><br>

    <label for="nameId">Имя:</label><br>
    <input type="text" id="nameId" name="name"><br>

    <label for="surnameId">Фамилия:</label><br>
    <input type="text" id="surnameId" name="surname"><br>

    <label for="cityId">Город:</label><br>
    <input type="text" id="cityId" name="city"><br>

    <label for="streetId">Улица:</label><br>
    <input type="text" id="streetId" name="street"><br>

    <label for="buildingId">Номер дома:</label><br>
    <input type="text" id="buildingId" name="building"><br>

    <label for="flatId">Номер квартиры:</label><br>
    <input type="text" id="flatId" name="flat"><br>

    <label for="passportId">Номер пасспорта:</label><br>
    <input type="text" id="passportId" name="passport"><br>

    <input type="submit" value="Registration">
</form>

<c:if test="${ param.error == true}">
    Пользователь с данным email уже существует
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
