<%--
  Created by IntelliJ IDEA.
  User: Florian
  Date: 02.10.2018
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>BigData - Assignment2</title>
</head>

<body>
<h1>Please enter a number of throws: </h1>
<form name="getpiresult" action="getpiresult" method="POST" >
    <label>Throws of darts:</label>
    <input type='text' name='throws' />
    <input type="submit" value="Get all results">
</form>
<h1>All Pi Results</h1>
<c:if test="${not empty lists}">
    <c:forEach items="${lists}" var="lists">
        <p>
                ${lists}
            <br>
        </p>

    </c:forEach>
</c:if>
</body>
</html>
