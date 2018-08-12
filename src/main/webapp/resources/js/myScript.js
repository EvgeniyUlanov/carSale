var $url = window.location.pathname;
var userLogin;
var announcementList;
var navigation;

$(document).ready(function () {
    navigation = $('.navbar');
    HideContentAndShowFirst();
    navigation.find('.nav-item').click(function () {
        $('#nav').find('nav-item').removeClass('active');
        $(this).addClass('active');

        $('.content').hide();
        var activeTab = $(this).attr('href');
        $(activeTab).fadeIn('slow');
        return false;
    });
    ShowSignMenu();
    FillAnnouncements();
});

function HideContentAndShowFirst() {
    $('.content').hide();
    navigation.find('.nav-item:first').addClass('active').show();
    $('.content:first').show();
}

function GetUser() {
    var login = $('#userLogin').val();
    var password = $('#userPassword').val();
    $.ajax({
        type: 'POST',
        url: $url + 'user/getByLogin',
        data: {'login': login, 'password': password},
        async: false,
        success: function (response) {
            if (response == '0') {
                alert("user is not exist");
            } else if (response == '1') {
                alert("wrong password")
            } else {
                userLogin = response;
                ShowSignedUser();
            }
        }
    });
}

function SignOut() {
    $.ajax({
        type: 'GET',
        url: $url + 'signOut',
        async: false,
        success: function () {
            userLogin = undefined;
            $('#userLabel').text('');
            ShowSignMenu();
        }
    });
}

function ShowSignedUser() {
    var userLabel = 'logged as ' + userLogin;
    $('#userLabel').text(userLabel);
    $('#signInForm').hide();
    $('#userSignIn').show();
}

function ShowSignMenu() {
    GetCurrentUser();
    if (userLogin === undefined) {
        $('#signInForm').show();
        $('#userSignIn').hide();
    } else {
        ShowSignedUser();
    }
}

function GetCurrentUser() {
    $.ajax({
        type: 'POST',
        url: $url + 'user/getCurrent',
        async: false,
        success: function (result) {
            if (result == '$undefined') {
                userLogin = undefined;
            } else {
                userLogin = result;
            }
        }
    });
}

function FillAnnouncements() {
    GetAllAnnouncements();
    $.each(announcementList, function (index, announcement) {
        var divAnnoun = $('<div>').addClass('row').appendTo($('#announcementsList'));
        var divImage = $('<div>').addClass('image col-3').appendTo(divAnnoun);
        var divInfo = $('<div>').addClass('info col-6').appendTo(divAnnoun);
        $('<p>').text('Brand: ' + announcement.carBrand).appendTo(divInfo);
        $('<p>').text('Model: ' + announcement.carModel).appendTo(divInfo);
        $('<p>').text('Price: ' + announcement.price).appendTo(divInfo);
        $('<p>').text('Description: ' + announcement.description).appendTo(divInfo);
        $('<p>').text('Contacts: ' + announcement.contact).appendTo(divInfo);
        $('<hr>').appendTo($('#announcementsList'));
    });
}

function GetAllAnnouncements() {
    $.ajax({
        type: 'GET',
        url: $url + 'announcement/getAll',
        async: false,
        success: function (response) {
            announcementList = response;
        }
    });
}

function AddAnnouncement() {
    var carBrand = $('#carBrand').val();
    var carModel = $('#carModel').val();
    var price = $('#price').val();
    var description = $('#description').val();
    var contact = $('#contact').val();
    var announcement = {
        'carBrand': carBrand,
        'carModel': carModel,
        'price': price,
        'description': description,
        'contact': contact
    };
    $.ajax({
        type: 'POST',
        url: $url + 'announcement/add',
        data: announcement,
        async: false,
        success: function () {
            HideContentAndShowFirst();
            FillAnnouncements();
        }
    });
}

function RegisterUser() {
    var newUserName = $('#newUserName').val();
    var newUserLogin = $('#newUserLogin').val();
    var newUserPassword = $('#newUserPassword').val();
    $.ajax({
       type: 'POST',
       url: $url + 'user/add',
       data: {'name': newUserName, 'login': newUserLogin, 'password': newUserPassword},
       async: false,
       success: function () {
           HideContentAndShowFirst();
       }
    });
}
