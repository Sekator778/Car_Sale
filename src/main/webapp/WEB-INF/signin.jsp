<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign In</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function validate() {
            var name = $('#name').val();
            var pass = $('#pass').val();
            var msg = "";
            if (name === "") {
                msg += " name";
            }
            if (pass === "") {
                if (msg !== "") {
                    msg += " and "
                }
                msg += "password."
            }
            if (msg !== "") {
                alert("Please enter: " + massage);
            } else {
                var user = {
                    name: name,
                    pass: pass
                };
                response(user);
            }
            return false;
        }

        function response(user) {
            var url = "${pageContext.servletContext.contextPath}/signin";
            $ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(user),
                success: [function ($data) {
                    resultProcessing($data);
                }],
                data_type: 'json'
            });
        }
        function resultProcessing($data) {
            if ($data['status'] === 'valid') {
                var url = "${pageContext.servletContext.contextPath}/login";
                document.location.href = url;
            } else {
                alert("invalid credential");
                document.location.reset();
            }
        }
    </script>
</head>
<body>
<form name="dataForm" class="container">
    <div>
        <label for="name">Name: </label>
        <input type="text" class="form-control" name="name" placeholder="enter name" id="name">
    </div>
    <div>
        <label for="pass">Password: </label>
        <input type="password" class="form-control" name="password" placeholder="enter password" id="pass">
    </div>
    <div>
        <br>
        <input type="submit" class="form-control" onclick="return validate()" value="Sign In">
    </div>
</form>
</body>
</html>
