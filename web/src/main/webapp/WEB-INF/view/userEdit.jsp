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
<h2>Адрес: ${user.city}, ${user.street}, ${user.building}, ${user.flat}</h2>
<h2>Номер паспорта: ${user.passport}</h2>

<form action="${pageContext.request.contextPath}/users/update/${user.id}" method="post">
    <label for="emailId">Email:</label><br>
    <input type="email" id="emailId" name="email" value="${user.email}"><br>

    <label for="nameId">Имя:</label><br>
    <input type="text" id="nameId" name="name" value="${user.name}"><br>

    <label for="surnameId">Фамилия:</label><br>
    <input type="text" id="surnameId" name="surname" value="${user.surname}"><br>

    <label for="roleId">Роль:</label><br>
    <select name="role" id="roleId">
        <option value="ADMIN">ADMIN</option>
        <option value="MANAGER">MANAGER</option>
        <option value="USER">USER</option>
    </select>

    <label for="cityId">Город:</label><br>
    <input type="text" id="cityId" name="city" value="${user.city}"><br>

    <label for="streetId">Улица:</label><br>
    <input type="text" id="streetId" name="street" value="${user.street}"><br>

    <label for="buildingId">Номер дома:</label><br>
    <input type="text" id="buildingId" name="building" value="${user.building}"><br>

    <label for="flatId">Номер квартиры:</label><br>
    <input type="text" id="flatId" name="flat" value="${user.flat}"><br>

    <label for="passportId">Номер пасспорта:</label><br>
    <input type="text" id="passportId" name="passport" value="${user.passport}"><br>

    <input type="submit" value="Изменить">

</form>

<c:if test="${param.error == true}">
    Пользователь не изменен
</c:if>

<%@ include file="footer.jsp" %>
</body>
</html>
