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
            var type = $('#type').val();
            var brand = $('#brand').val();
            var model = $('#model').val();
            var usage = $('#usage').val();
            var year = $('#year').val();
            var desc = $('#desc').val();
            var price = $('#price').val();
            var pic = document.getElementById("pic").files[0];
            if (type === ""
                || brand === ""
                || model === ""
                || usage === ""
                || year === ""
                || desc === ""
                // || pic === undefined
                || price === "") {
                alert("Please, fill all fields in.");
            } else {
                var user = {
                    type: type,
                    brand: brand,
                    model: model,
                    usage: usage,
                    year: year,
                    desc: desc,
                    price: price,
                    picPath: ""
                };
                if (pic !== undefined) {
                    var formData = new FormData();
                    formData.append("pic", pic);
                    loadPicture(formData, user);
                } else {
                    response(user);
                }
            }
            return false;
        }

        function loadPicture(formData, user) {
            var url = "${pageContext.servletContext.contextPath}/upload";
            $.ajax({
                type: "post",
                url: url,
                data: formData,
                success: [function ($data) {
                    user['picPath'] = $data['pic'];
                    response(user);
                }],
                contentType: false,
                processData: false,
            });
        }

        function response(user) {
            var url = "${pageContext.servletContext.contextPath}/add";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(user),
                success: [function ($data) {
                    resultProcessing($data);
                }],
                dataType: 'json'
            });
        }

        function resultProcessing($data) {
            if ($data['status'] === 'valid') {
                var url = "${pageContext.servletContext.contextPath}/cabinet";
                document.location.href = url;
            } else {
                alert("Sorry, the form was not added. Please, try again later.");
                document.dataForm.reset();
            }
        }

        function toCabinet() {
            var url = "${pageContext.servletContext.contextPath}/cabinet";
            document.location.href = url;
        }
    </script>
</head>
<body>
<form name="dataForm" class="container">
    <div>
        <label for="type">Type: </label>
        <input type="text" class="form-control" name="type" placeholder="enter the type" id="type">
    </div>
    <div>
        <label for="brand">Brand: </label>
        <input type="text" class="form-control" name="brand" placeholder="enter the brand" id="brand">
    </div>
    <div>
        <label for="model">Model: </label>
        <input type="text" class="form-control" name="model" placeholder="enter the model" id="model">
    </div>
    <div>
        <label for="usage">Usage of vehicle (km): </label>
        <input type="number" class="form-control" name="usage" placeholder="enter the usage" id="usage">
    </div>
    <div>
        <label for="year">Year of manufacture: </label>
        <input type="number" class="form-control" name="year" placeholder="enter the year" id="year">
    </div>
    <div>
        <label for="desc">Description: </label>
        <textarea class="form-control" name="desc" placeholder="enter the description" id="desc"></textarea>
    </div>
    <div>
        <label for="price">Price in RUB: </label>
        <input type="number" class="form-control" name="price" placeholder="enter the price" id="price">
    </div>
    <div>
        <label for="pic">Picture: </label>
        <input type="file" class="form-control" name="pic" id="pic">
    </div>
    <div>
        <table class="table">
            <tr>
                <td>
                    <input type="button" class="form-control" onclick="return validate();" value="Add">
                </td>
                <td>
                    <input type="button" class="form-control" onclick="return toCabinet();" value="return">
                </td>
            </tr>
        </table>
    </div>
</form>
</body>
</html>