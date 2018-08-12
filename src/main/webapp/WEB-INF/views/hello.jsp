<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car sale</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <%--<link href="resources/css/styles.css" type="text/css" rel="stylesheet"/>--%>
    <script type="text/javascript" src="resources/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="resources/js/myScript.js"></script>
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">Car sale</a>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="#announcements">Announcements</a>
                <a class="nav-item nav-link" href="#newAnnouncement">Add new announcement</a>
                <a class="nav-item nav-link" href="#registerUser">Register</a>
            </div>
        </div>
        <div id="signInForm">
            <input placeholder="login" id="userLogin"/>
            <input placeholder="password" id="userPassword"/>
            <input class="btn btn-primary" type="submit" value="Sign in" onclick="GetUser()">
        </div>
        <div id="userSignIn" class="form-inline">
            <div class="col-auto">
                <label style="color: white" id="userLabel"></label>
            </div>
            <div class="col-auto">
                <input class="btn btn-primary" type='submit' value='sign out' onclick="SignOut()"/>
            </div>
        </div>
    </div>
</nav>

<div class="container content" id="announcements">
    <div id="usersTable">
        <h1>Announcements of car sale</h1>
        <div id="announcementsList"></div>
        <table id="announTable" class="table">
        </table>
        <button class="btn btn-primary" onclick="GetAllAnnouncements()">Get All announcements</button>
    </div>
</div>

<div class="container content" id="newAnnouncement">
    <div class="row justify-content-center align-items-center">
        <form>
            <label for="carBrand">Car brand:</label>
            <input class="form-control" id="carBrand" placeholder="enter car brand"/>
            <label for="carModel">Car model:</label>
            <input class="form-control" id="carModel" placeholder="enter car model" required/>
            <label for="price">Price:</label>
            <input class="form-control" id="price" placeholder="enter car price"/>
            <label for="description">Description:</label>
            <textarea id="description" class='form-control'></textarea>
            <label for="contact">Contact info:</label>
            <input class="addressSelect form-control" id="contact" placeholder="enter user address"/>
            <button class="btn btn-primary" onclick="AddAnnouncement()">Add announcement</button>
        </form>
    </div>
</div>

<div class="container content" id="registerUser">
    <div class="row justify-content-center align-items-center">
        <h1>Register new User</h1>
        <form>
            <label for="newUserName">Name:</label>
            <input class="form-control" id="newUserName" placeholder="enter your name"/>
            <label for="newUserLogin">Login:</label>
            <input class="form-control" id="newUserLogin" placeholder="enter your login" required/>
            <label for="newUserPassword">Password:</label>
            <input class="form-control" id="newUserPassword" placeholder="enter your password"/>
            <button class="btn btn-primary" onclick="RegisterUser()">Register</button>
        </form>
    </div>
</div>

</body>
</html>
