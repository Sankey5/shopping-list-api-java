<%@ taglib tagdir="/WEB-INF/tags" prefix="sl" %>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <title>Mist Library Task 1-Landing</title>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
    <script src="resources/static/js/app.js" /></script>
    <script src="resources/static/js/service/groceryList.service.js"></script>
    <script src="resources/static/js/controller/groceryList.controller.js"></script>
    <style type="text/css">
        body {
        	background-image:
        		url('https://ak6.picdn.net/shutterstock/videos/1024598666/thumb/1.jpg');
        	background-repeat: no-repeat;
        	background-size: cover;
        }
    </style>
    <link rel="apple-touch-icon" sizes="180x180" href="/android-chrome-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="resources/static/images/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="16x16" href="resources/static/images/favicon-16x16.png">
</head>

<body>
   <sl:myNav/>
    <br>
    <div class="container">
        <div class="row">
            <div class="col">
                <sl:recipeList/>
            </div>
        </div>
    </div>
</body>

</html>