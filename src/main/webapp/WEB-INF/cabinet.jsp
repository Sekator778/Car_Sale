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
        function sold(status) {
            var result;
            if (status) {
                result = "Sold";
            } else {
                result = "Open";
            }
            return result;
        }

        function changeStatus(id) {
            var status = document.getElementById(id + "_cb").checked;
            var changes = {
                id: id,
                status: status,
            };
            var url = "${pageContext.servletContext.contextPath}/change";
            $.ajax({
                type: "POST",
                url: url,
                success: [function ($data) {
                    if ($data['status'] === "valid") {
                        if (status) {
                            document.getElementById(id + "_div").innerHTML = "Status: Sold";
                        } else {
                            document.getElementById(id + "_div").innerHTML = "Status: Open";
                        }
                    }
                }],

                data: JSON.stringify(changes),
                dataType: 'json'
            });
            return false;
        }
    </script>
    <style>
        body {
            padding: 30px;
            background-color: #ffffff;

        }
    </style>
<body>
<div class="container" style="background-color: #D2D50C">
    <h4 class="text-center">Welcome <c:out value="${name}"></c:out>, that's your personal cabinet.</h4>
</div>


<div class="container">
    <div class="row" >
        <div class="col-sm-4 col-md-offset-2  col-sm-offset-2 col-lg-offset-2   col-lg-4 col-md-4 col-sm-4 col-xs-12" style="text-align:center;">
            <button type="button" class="btn btn-primary btn-block" onclick="newCar()">Add a new Car for Sale</button>
        </div>
        <div class="col-sm-4 col-lg-4 col-md-4 col-sm-4 col-xs-12" style="text-align:center;">
            <button type="button" class="btn btn-primary btn-block" onclick="startPage()">Go to start page</button>
        </div>

    </div>
<div class="container">
    <table id="table" class="table">
        <c:forEach items="${list}" var="list">
            <tr>
                <td width="250px">
                    <form>
                        <a href="${pageContext.servletContext.contextPath}/download?pic=${list.picture}">
                            <img src="${pageContext.servletContext.contextPath}/download?pic=${list.picture}"
                                 width="200px" height="200px">
                        </a>
                    </form>
                </td>
                <td>
                    <div id="${list.id}_div">
                        Status:
                        <script type='text/javascript'>
                            document.write(sold(${list.sold}));
                        </script>
                    </div>
                    <br>
                    Price:
                    <c:out value="${list.price}"></c:out>
                    <br>
                    Type:
                    <c:out value="${list.type}"></c:out>
                    <br>
                    Brand:
                    <c:out value="${list.brand}"></c:out>
                    <br>
                    Model:
                    <c:out value="${list.model}"></c:out>
                    <br>
                    Year:
                    <c:out value="${list.year}"></c:out>
                    <br>
                    Usage of vehicle:
                    <c:out value="${list.usage}"></c:out>
                    <br>
                    Description:
                    <c:out value="${list.description}"></c:out>
                </td>
                <td>
                    <input type="checkbox" id="${list.id}_cb"
                           onchange="changeStatus(${list.id}, ${list.sold})">
                    <script type='text/javascript'>
                        document.getElementById(${list.id} +"_cb").checked = ${list.sold};
                    </script>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</div>
</body>
</html>
