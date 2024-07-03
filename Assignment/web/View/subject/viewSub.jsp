<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Subject List</title>
        <link rel="stylesheet" type="text/css" href="../CSS/subject.css">
        <script src="../JavaScript/viewAssesments.js"></script> 
    </head>
    <body>
        <div class="container">
            <h1>Subject List</h1>

            <table class="subject-table">
                <thead>
                    <tr>
                        <th>Subject ID</th>
                        <th>Subject Name</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="subject" items="${subject}">
                        <tr style="cursor: pointer;">
                            <td>${subject.id}</td>
                            <td onclick="goToAssessment(${subject.id}, event)">${subject.name}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <br/>
        <a href="#" onclick="navigateHomeofSubject()">Home</a> <br/><br/>
        <a href="http://localhost:1407/Assignment/logout">Logout</a>
    </body>
</html>
