<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mgl" %>
    <!DOCTYPE html>
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <html>
        <head>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.8/angular.min.js"></script>
        <script src="resources/static/js/app.js" /></script>
        <script src="resources/static/js/service/MGL_Task1.service.js"></script>
        <script src="resources/static/js/controller/MGL_Task1.controller.js"></script>
            <!-- Bootstrap CSS -->
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

            <title>Mist Library Task 1-review</title>
            <style type="text/css">
                body {
                	background-image:
                		url('https://ak6.picdn.net/shutterstock/videos/1024598666/thumb/1.jpg');
                	background-repeat: no-repeat;
                	background-size: cover;
                }
            </style>
        </head>

        <body>
           <mgl:myNav/>
            <div id="ReviewFormDiv" class="container">
                <br>
                <form name="submitReviewForm" method="POST" action="addReview">
                    <table class="table table-dark text-light">
                        <tr>
                            <td><label>Review Body*</label></td>
                            <td><textarea name="reviewBody" class="form-control" placeholder="What did you like/dislike about the game? [required]" required></textarea></td>
                        </tr>
                        <tr>
                            <td><label>Author</label></td>
                            <td><input type="text" name="author" class="form-control" placeholder="Your name (leave black to leave an anonymous review)"></input>
                            </td>
                        </tr>
                        <tr>
                            <td><label>Rating</label></td>
                            <td><select id="ratingSelection" name="rating" class="chzn-select" style="width: 100px" required>
							<option value=1>1</option>
							<option value=2>2</option>
							<option value=3>3</option>
							<option value=4>4</option>
							<option value=5>5</option>
					</select></td>
                        </tr>
                        <tr>
                            <td><input type="submit"></td>
                        </tr>
                    </table>
                </form>
            </div>
        </body>
        <script type="text/javascript"></script>

        </html>