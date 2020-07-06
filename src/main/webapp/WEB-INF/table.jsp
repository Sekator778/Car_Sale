<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cars</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        /**
         new user
         */
        function registration() {
            var url = "${pageContext.servletContext.contextPath}/registration";
            document.location.href = url;
        }

        function login() {
            var url = "${pageContext.servletContext.contextPath}/login";
            document.location.href = url;
        }

        /**
         * check status sold or no
         */
        function sold(status) {
            var result;
            if (status) {
                result = "Sold";
            } else {
                result = "Open";
            }
            return result;
        }

        /**
         * use custom filter
         */
        function filter() {
            var day = document.getElementById("day").checked;
            var photo = document.getElementById("photo").checked;
            var brand = document.getElementById("brand").value;
            var url = "${pageContext.servletContext.contextPath}/filter?day="
                + day + "&photo=" + photo + "&brand=" + brand;
            document.location.href = url;
        }
    </script>
    <style>
        body {
            background: linear-gradient(70deg, #e6e6e6, #f7f7f7);
            color: #514B64;
            min-height: 100vh;
            padding: 30px;
        }
        .button {
            background-color: mediumslateblue;
            border: 1px;
            color: white;
            padding: 15px 32px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 18px;
            width: 100%;
            border-radius: 8px;
        }

        /*.table tr td { width:33%; }*/
    </style>

</head>
<body>
<a href="#" class="navbar-left" style="color:red;font-size:40px;"><img
        src="https://res.cloudinary.com/mhmd/image/upload/v1557368579/logo_iqjuay.png"> Car Sale ALEX</a>
<div class="container">
    <table id="head" class="table">
        <tr>
            <td>
                <button class="button" onclick="registration()" id="mainButton">Registration</button>
            </td>
            <td>
                <button class="button" onclick="login()"><c:out value="${name}"></c:out></button>
            </td>
        </tr>
    </table>
    <table id="filter" class="table">
        <tr>
            <div>
                <label for="day">Last Day: </label>
                <input type="checkbox" id="day" name="day" value="day">
                <script>
                    document.getElementById("day").checked = ${day};
                </script>
            </div>
        </tr>
        <tr>
            <div>
                <label for="photo">Auto with photo only</label>
                <input type="checkbox" id="photo" name="photo" value="photo">
                <script>
                    document.getElementById("photo").cheked = ${photo};
                </script>
            </div>
        </tr>
        <tr>
            <div>
                <select name="brand" class="form-control" id="brand">
                    <option value="none"></option>
                    <c:forEach items="${brands}" var="brand">
                        <option value="${brand}"><c:out value="${brand}"></c:out></option>
                    </c:forEach>
                </select>
                <script type="text/javascript">
                    document.getElementById("brand").value = "${setBrand}";
                </script>
            </div>
        </tr>
        <tr>
            <button class="form-control" onclick="filter()">Apply filters</button>
        </tr>
    </table>
</div>
<table>
    <div class="container">
        <table id="table" class="table">
            <c:forEach items="${list}" var="list">
                <tr>
                    <td width="25%">
                        <form>
                            <a href="${pageContext.servletContext.contextPath}/download?pic=${list.picture}">
                                <img src="${pageContext.servletContext.contextPath}/download?pic=${list.picture}"
                                     width="250px" height="200px"/>
                            </a>
                        </form>
                    </td>
                    <td width="35%">
                        Status:
                        <script type="text/javascript">
                            document.write(sold(${list.sold}))
                        </script>
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
                    <td><h2><strong> Тут может быть ваша реклама </strong></h2>
                        <a href="https://job4j.ru/">
                            <img src="http://i.piccy.info/i9/b97aa3efe3fca0f2597f2dce8af4ef9b/1594042746/571299/1386696/reklama.png"
                                 width="300" height="200" align="left"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</table>
</body>
</html>