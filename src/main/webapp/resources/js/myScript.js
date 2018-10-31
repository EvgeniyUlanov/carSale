var $url = window.location.pathname;
var userLogin;
var announcementList;
var navigation;
var filesToUpload;

$(document).ready(function () {
    navigation = $('.navbar');
    HideContentAndShowAnnouncements();
    navigation.find('.nav-item').click(function () {
        navigation.find('nav-item').removeClass('active');
        $(this).addClass('active');

        $('.content').hide();
        var activeTab = $(this).attr('href');
        $(activeTab).fadeIn('slow');
        return false;
    });
    ShowSignMenu();
    FillAnnouncements();
    $('.filesToUpload').on('change', function () {
        filesToUpload = this.files;
    });

    $('.upload_files').on('click', function (event) {
        event.stopPropagation();
        event.preventDefault();
        if (typeof filesToUpload === 'undefined') {
            return;
        }

        var data = new FormData();
        $.each(filesToUpload, function (key, value) {
            data.append(key, value);
        });

        $.ajax({
            url: $url + 'photo/add',
            type: 'post',
            data: data,
            cache: false,
            processData: false,
            contentType: false,
            success: function (responce) {
                $('.filesToUpload').prop('value', null);
                $('.addedImages').append(responce).append('<br>');
            },
            error: function () {
                alert("error");
            }
        })
    });
});

function HideContentAndShowAnnouncements() {
    $('.content').hide();
    navigation.find('#announcements').addClass('active').show();
    $('#announcements').show();
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
            if (response === '0') {
                alert("user is not exist");
            } else if (response === '1') {
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
    var userLabel = 'logged as ' + userLogin.login;
    $('#userLabel').text(userLabel);
    $('.showIfIsNotSigned').hide();
    $('#userSignIn').show();
    HideContentAndShowAnnouncements();
}

function ShowSignMenu() {
    GetCurrentUser();
    if (userLogin === undefined) {
        $('.showIfIsNotSigned').show();
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
            if (result === '$undefined') {
                userLogin = undefined;
            } else {
                userLogin = result;
                FillUserAnnouncements();
            }
        }
    });
}

function FillAnnouncements() {
    GetAllAnnouncements();
    $('.announcement').remove();
    $('.splitter').remove();
    $.each(announcementList, function (index, announcement) {
        var announList = $('#announcementsList');
        var divAnnoun = $('<div>').addClass('announcement row').appendTo(announList);
        var divImage = $('<div>').addClass('image col-3').appendTo(divAnnoun);
        var divInfo = $('<div>').addClass('info col-4').appendTo(divAnnoun);
        var divDescription = $('<div>').addClass('descr col-5').appendTo(divAnnoun);
        $('<p>').text('Brand: ' + announcement.car.brand).appendTo(divInfo);
        $('<p>').text('Model: ' + announcement.car.model).appendTo(divInfo);
        $('<p>').text('Price: ' + announcement.price).appendTo(divInfo);
        $('<p>').text('Description: ' + announcement.description).appendTo(divDescription);
        $('<p>').text('Contacts: ' + announcement.contactInfo).appendTo(divDescription);
        $('<p>').addClass('announId').text(announcement.id).hide().appendTo(divDescription);
        $('<button>').text('Details').addClass('details btn btn-primary').appendTo(divDescription);
        $('<hr>').addClass('splitter').appendTo(announList);
        getImage(announcement.id, divImage);
    });
    addActionToDetailsButton()
}

function addActionToDetailsButton() {
    $('.details').on('click', function () {
        var announcementId = $(this).closest("div").find(".announId").text();
        HideContentAndShowAnnouncementDetail();
        $.ajax({
            type: 'GET',
            url: $url + 'announcement/get',
            data: {'announcementId': announcementId},
            async: false,
            success: function (response) {
                $('#currentCarBrand').val(response.car.brand);
                $('#currentCarModel').val(response.car.model);
                $('#currentPrice').val(response.price);
                $('#currentDescription').text(response.description);
                $('#currentContact').val(response.contactInfo);
                $('#currentCarVin').val(response.car.vin);
                $('#currentCarYear').val(response.car.year);
                $('#currentRun').val(response.car.run);
                $('#currentColor').val(response.car.color);
                $('#currentEngineType').val(response.car.engineType);
                $('#currentEnginePower').val(response.car.enginePower);
                var divImage = $('#currentImage');
                divImage.find('img').remove();
                getImage(announcementId, divImage);
            }
        })
    });
}

function HideContentAndShowAnnouncementDetail() {
    $('.content').hide();
    navigation.find('#currentAnnouncement').addClass('active').show();
    $('#currentAnnouncement').show();
}

function getImage(announId, divImage) {
    var image = $('<img>');
    image.attr('src', 'image/get?announId=' + announId);
    image.appendTo(divImage);
}

function GetAllAnnouncements() {
    var filter = $('input[name=filter]:checked').val();
    var searchingBrand = $('#searchingBrand').val();
    $.ajax({
        type: 'GET',
        url: $url + 'announcement/getAll',
        data: {'filter': filter, 'searchingBrand': searchingBrand},
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
    var vin = $('#carVin').val();
    var year = $('#carYear').val();
    var run = $('#run').val();
    var color = $('#color').val();
    var engineType = $('#engineType').val();
    var enginePower = $('#enginePower').val();
    var announcement = {
        'carBrand': carBrand,
        'carModel': carModel,
        'price': price,
        'description': description,
        'contact': contact,
        'vin': vin,
        'year': year,
        'run': run,
        'color': color,
        'engineType': engineType,
        'enginePower': enginePower
    };
    $.ajax({
        type: 'POST',
        url: $url + 'announcement/add',
        data: announcement,
        async: false,
        success: function () {
            HideContentAndShowAnnouncements();
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
            HideContentAndShowAnnouncements();
        }
    });
}

function FillUserAnnouncements() {
    var $table = $('#announcementTable');
    $table.find('.removable').remove();
    $.ajax({
        type: 'GET',
        url: $url + 'announcement/getByUser',
        data: {'userId': userLogin.id},
        success: function (response) {
            $.each(response, function (index, announcement) {
                var tr = $('<tr>').addClass('removable').appendTo($table);
                $('<td>').addClass('announId').hide().text(announcement.id).appendTo(tr);
                $('<td>').text(announcement.car.brand).appendTo(tr);
                $('<td>').text(announcement.car.model).appendTo(tr);
                $('<td>').text(announcement.createdDate).appendTo(tr);
                $('<td>').text(announcement.price).appendTo(tr);
                $('<td>').text(announcement.isSold).appendTo(tr);
                var td = $('<td>').appendTo(tr);
                $('<button>').text('delete').addClass('deleteBtn btn btn-primary').appendTo(td);
                td = $('<td>').appendTo(tr);
                $('<button>').text('update').addClass('updateBtn btn btn-secondary').appendTo(td);
                td = $('<td>').appendTo(tr);
                $('<button>').text('close').addClass('closeBtn btn btn-primary').appendTo(td);
            });
            addActionToDeleteButton();
            addActionToCloseButton();
            addActionToUpdateButton();
        },
        error: function () {
            alert("error");
        }
    });
}

function addActionToDeleteButton() {
    $('.deleteBtn').on('click', function () {
        var announcementId = $(this).closest("tr").find(".announId").text();
        $.ajax({
            type: 'GET',
            url: $url + 'announcement/delete',
            data: {'announcementId': announcementId},
            async: false,
            success: function () {
                FillUserAnnouncements();
                FillAnnouncements();
            },
            error: function () {
                alert('error');
            }
        })
    })
}

function addActionToUpdateButton() {
    $('.updateBtn').on('click', function () {
        var announcementId = $(this).closest("tr").find(".announId").text();
        HideContentAndShowUpdateAnnouncement();
        $.ajax({
            type: 'GET',
            url: $url + 'announcement/get',
            data: {'announcementId': announcementId},
            async: false,
            success: function (response) {
                $('#announcmentId').val(response.id);
                $('#updateCarBrand').val(response.car.brand);
                $('#updateCarModel').val(response.car.model);
                $('#updatePrice').val(response.price);
                $('#updateDescription').text(response.description);
                $('#updateContact').val(response.contactInfo);
                $('#updateCarVin').val(response.car.vin);
                $('#updateCarYear').val(response.car.year);
                $('#updateRun').val(response.car.run);
                $('#updateColor').val(response.car.color);
                $('#updateEngineType').val(response.car.engineType);
                $('#updateEnginePower').val(response.car.enginePower);
                var divImage = $('#updateImage');
                divImage.find('img').remove();
                getImage(announcementId, divImage);
            }
        })
    });
}

function UpdateAnnouncement() {
    var id = $('#announcmentId').val();
    var carBrand = $('#updateCarBrand').val();
    var carModel = $('#updateCarModel').val();
    var price = $('#updatePrice').val();
    var description = $('#updateDescription').val();
    var contact = $('#updateContact').val();
    var vin = $('#updateCarVin').val();
    var year = $('#updateCarYear').val();
    var run = $('#updateRun').val();
    var color = $('#updateColor').val();
    var engineType = $('#updateEngineType').val();
    var enginePower = $('#updateEnginePower').val();
    var announcement = {
        'id': id,
        'carBrand': carBrand,
        'carModel': carModel,
        'price': price,
        'description': description,
        'contact': contact,
        'vin': vin,
        'year': year,
        'run': run,
        'color': color,
        'engineType': engineType,
        'enginePower': enginePower
    };
    $.ajax({
        type: 'POST',
        url: $url + 'announcement/update',
        data: announcement,
        async: false,
        success: function () {
            HideContentAndShowAnnouncements();
            FillAnnouncements();
        }
    });
}

function HideContentAndShowUpdateAnnouncement() {
    $('.content').hide();
    navigation.find('#updateAnnouncement').addClass('active').show();
    $('#updateAnnouncement').show();
}

function addActionToCloseButton() {
    $('.closeBtn').on('click', function () {
        var announcementId = $(this).closest("tr").find(".announId").text();
        $.ajax({
            type: 'GET',
            url: $url + 'announcement/close',
            data: {'announcementId': announcementId},
            async: false,
            success: function () {
                FillUserAnnouncements();
            },
            error: function () {
                alert('error');
            }
        })
    })
}

