/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('#logIn').click(function () {
    logIn();
});

function make_base_auth(user, password) {
    var tok = user + ':' + password;
    var hash = btoa(tok);
    return "Basic " + hash;
}

function logIn() {
var username = document.getElementById("username").value;
var password = document.getElementById("password").value;
    $.ajax({
        type: "POST",
        url: "http://bulima-cms-devel.htec.co.rs/CMS_Bulima-1.0/rest/user/login",
        dataType: "json",
        headers: { 
            'Content-Type': 'application/json' 
        },
        data: 'Branko',
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', make_base_auth(username, password));
        },
        success: function (response) {
            alert(response);
        }   
    });
}