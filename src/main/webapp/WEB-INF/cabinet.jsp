<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Cabinet</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function newCar() {
            var url = "${pageContext.servletContext.contextPath}/add";
            document.location.href = url;
        }
        function startPage() {
            var url = "${pageContext.servletContext.contextPath}/";
            document.location.href = url;
        }

    </script>
<body>
<h2>Welcome <c:out value="${name}"></c:out>, that's your personal cabinet.</h2>
<div class="container">
    <table id="buttons" class="table">
        <tr>
            <button class="form-control" onclick="newCar()">Add a new Car for Sale</button>
        </tr>
        <tr>
            <button class="form-control" onclick="startPage()">Go to start page</button>
        </tr>
    </table>
</div>

</body>
</html>
