<!DOCTYPE html>
<html>
<head>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome to Spring Web MVC project</title>
</head>

    <body>
    <h1>All Pi Results</h1>
    <c:if test="${not empty lists}">
        <c:forEach items="${lists}" var="lists">
            ${lists}
        </c:forEach>
    </c:if>
    </body>
</html>