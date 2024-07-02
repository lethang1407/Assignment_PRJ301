<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>View Assessments</title>
        <link rel="stylesheet" type="text/css" href="../CSS/assesment.css">
    </head>
    <body>
        <h2>Assessments</h2>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Weight</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="assessment" items="${assesments}">
                    <tr>
                        <td>${assessment.name}</td>
                        <td><fmt:formatNumber value="${assessment.weight * 100}" type="number" maxFractionDigits="0"/>%</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>