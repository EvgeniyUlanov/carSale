<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Car sale</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="resources/css/style.css" type="text/css" rel="stylesheet"/>
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
            </div>
        </div>
        <div class="showIfIsNotSigned form-inline">
            <a class="nav-item nav-link" href="#registerUser">Register</a>
            <a class="nav-item nav-link" href="#signInUser">Sign in</a>
        </div>
        <div id="userSignIn" class="form-inline">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="#myAnnouncements">My Announcements</a>
                <a class="nav-item nav-link" href="#newAnnouncement">Add new announcement</a>
            </div>
            <div class="col-auto">
                <label style="color: white" id="userLabel"></label>
            </div>
            <div class="col-auto">
                <input class="btn btn-primary" type='submit' value='sign out' onclick="SignOut()"/>
            </div>
        </div>
    </div>
</nav>

<div class="container content h-100" id="signInUser">
    <div class="row justify-content-center align-items-center">
        <form>
            <div class="form-group">
                <input class="form-control" placeholder="login" id="userLogin"/>
                <input class="form-control" placeholder="password" id="userPassword"/>
                <input class="btn btn-primary" type="submit" value="Sign in" onclick="GetUser()">
            </div>
        </form>
    </div>
</div>

<div class="container content" id="announcements">
    <div id="filter">
        <p><input type="radio" name="filter" value="all" checked>All announcement</p>
        <p><input type="radio" name="filter" value="last_day">For the last day</p>
        <p><input type="radio" name="filter" value="brand">By brand <input id="searchingBrand"></p>
        <p><input type="radio" name="filter" value="with_photo">With photo</p>
        <p><input type="button" value="Filter" onclick="FillAnnouncements()"></p>
    </div>
    <div id="usersTable">
        <h1>Announcements of car sale</h1>
        <div id="announcementsList"></div>
    </div>
</div>

<div class="container content" id="newAnnouncement">
    <div class="row justify-content-center align-items-center">
        <div class="col-4">
            <form>
                <label for="carBrand">Car brand:</label>
                <input class="form-control" id="carBrand" placeholder="enter car brand" required/>
                <label for="carModel">Car model:</label>
                <input class="form-control" id="carModel" placeholder="enter car model" required/>
                <label for="price">Price:</label>
                <input class="form-control" id="price" placeholder="enter car price" required/>
                <label for="description">Description:</label>
                <textarea id="description" class='form-control'></textarea>
                <label for="contact">Contact info:</label>
                <input class="addressSelect form-control" id="contact" placeholder="enter contacts"/>
            </form>
        </div>
        <div class="col-4">
            <label for="carVin">VIN:</label>
            <input class="form-control" id="carVin" placeholder="enter VIN" required/>
            <label for="carYear">Year of production:</label>
            <input class="form-control" id="carYear" placeholder="enter year" required/>
            <label for="run">Run:</label>
            <input class="form-control" id="run" placeholder="enter run" required/>
            <label for="color">Color:</label>
            <input id="color" class='form-control' placeholder="enter color"/>
            <label for="engineType">Engine type:</label>
            <input class="form-control" id="engineType" placeholder="enter engine type"/>
            <label for="enginePower">Engine power:</label>
            <input class="form-control" id="enginePower" placeholder="enter engine power"/>
        </div>
        <div class="col-4">
            Select image to add:<input class="filesToUpload" type="file" name="fileName" id="photo">
            <button class="upload_files button btn-primary">Add image</button>
            <div class="addedImages">
            </div>
        </div>
    </div>
    <div class="row justify-content-center align-items-center">
        <div class="col-9">
            <button class="btn btn-primary btn-block" onclick="AddAnnouncement()">Add announcement</button>
        </div>
    </div>
</div>

<div class="container content" id="registerUser">
    <h1 class="row justify-content-center align-items-center">Register new User</h1>
    <div class="row justify-content-center align-items-center">
        <form>
            <label for="newUserName">Name:</label>
            <input class="form-control" id="newUserName" placeholder="enter your name" required/>
            <label for="newUserLogin">Login:</label>
            <input class="form-control" id="newUserLogin" placeholder="enter your login" required/>
            <label for="newUserPassword">Password:</label>
            <input class="form-control" id="newUserPassword" placeholder="enter your password" required/>
            <button class="btn btn-primary" onclick="RegisterUser()">Register</button>
        </form>
    </div>
</div>

<div class="container content" id="myAnnouncements">
    <div id="myAnnouncementList">
        <h1>My announcements</h1>
        <table id="announcementTable" class="table">
            <tr>
                <th>Car brand</th>
                <th>Car model</th>
                <th>Create date</th>
                <th>Price</th>
                <th>Is sold</th>
                <th>Update</th>
                <th>Delete</th>
                <th>Close</th>
            </tr>
        </table>
    </div>
</div>

<div class="container content" id="currentAnnouncement">
    <h1>Current Announcement</h1>
    <div class="row justify-content-center align-items-center">
        <div class="col-4">
            <form>
                <label for="currentCarBrand">Car brand:</label>
                <input class="form-control" id="currentCarBrand" placeholder="enter car brand" readonly/>
                <label for="currentCarModel">Car model:</label>
                <input class="form-control" id="currentCarModel" placeholder="enter car model" readonly/>
                <label for="currentPrice">Price:</label>
                <input class="form-control" id="currentPrice" placeholder="enter car price" readonly/>
                <label for="currentDescription">Description:</label>
                <textarea id="currentDescription" class='form-control' readonly></textarea>
                <label for="currentContact">Contact info:</label>
                <input class="addressSelect form-control" id="currentContact" placeholder="enter contacts" readonly/>
            </form>
        </div>
        <div class="col-4">
            <label for="currentCarVin">VIN:</label>
            <input class="form-control" id="currentCarVin" placeholder="enter VIN" readonly/>
            <label for="currentCarYear">Year of production:</label>
            <input class="form-control" id="currentCarYear" placeholder="enter year" readonly/>
            <label for="currentRun">Run:</label>
            <input class="form-control" id="currentRun" placeholder="enter run" readonly/>
            <label for="currentColor">Color:</label>
            <input id="currentColor" class='form-control' placeholder="enter color" readonly/>
            <label for="currentEngineType">Engine type:</label>
            <input class="form-control" id="currentEngineType" placeholder="enter engine type" readonly/>
            <label for="currentEnginePower">Engine power:</label>
            <input class="form-control" id="currentEnginePower" placeholder="enter engine power" readonly/>
        </div>
        <div class="col-4" id="currentImage">
        </div>
    </div>
</div>

<div class="container content" id="updateAnnouncement">
    <h1>Current Announcement</h1>
    <div class="row justify-content-center align-items-center">
        <div class="col-4">
            <form>
                <input hidden id="announcmentId">
                <label for="updateCarBrand">Car brand:</label>
                <input class="form-control" id="updateCarBrand" placeholder="enter car brand"/>
                <label for="updateCarModel">Car model:</label>
                <input class="form-control" id="updateCarModel" placeholder="enter car model"/>
                <label for="updatePrice">Price:</label>
                <input class="form-control" id="updatePrice" placeholder="enter car price"/>
                <label for="updateDescription">Description:</label>
                <textarea id="updateDescription" class='form-control'></textarea>
                <label for="updateContact">Contact info:</label>
                <input class="addressSelect form-control" id="updateContact" placeholder="enter contacts"/>
            </form>
        </div>
        <div class="col-4">
            <label for="updateCarVin">VIN:</label>
            <input class="form-control" id="updateCarVin" placeholder="enter VIN"/>
            <label for="updateCarYear">Year of production:</label>
            <input class="form-control" id="updateCarYear" placeholder="enter year"/>
            <label for="updateRun">Run:</label>
            <input class="form-control" id="updateRun" placeholder="enter run"/>
            <label for="updateColor">Color:</label>
            <input id="updateColor" class='form-control' placeholder="enter color"/>
            <label for="updateEngineType">Engine type:</label>
            <input class="form-control" id="updateEngineType" placeholder="enter engine type"/>
            <label for="updateEnginePower">Engine power:</label>
            <input class="form-control" id="updateEnginePower" placeholder="enter engine power"/>
        </div>
        <div class="col-4" id="updateImage">
        </div>
    </div>
    <div class="row justify-content-center align-items-center">
        <div class="col-9">
            <button class="btn btn-primary btn-block" onclick="UpdateAnnouncement()">Update Announcement</button>
        </div>
    </div>
</div>


</body>
</html>
