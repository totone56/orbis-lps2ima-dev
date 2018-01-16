/*
 * OrbisServer is an OSGI web application to expose OGC services.
 *
 * OrbisServer is part of the OrbisGIS platform
 *
 * OrbisGIS is a java GIS application dedicated to research in GIScience.
 * OrbisGIS is developed by the GIS group of the DECIDE team of the
 * Lab-STICC CNRS laboratory, see <http://www.lab-sticc.fr/>.
 *
 * The GIS group of the DECIDE team is located at :
 *
 * Laboratoire Lab-STICC – CNRS UMR 6285
 * Equipe DECIDE
 * UNIVERSITÉ DE BRETAGNE-SUD
 * Institut Universitaire de Technologie de Vannes
 * 8, Rue Montaigne - BP 561 56017 Vannes Cedex
 *
 * OrbisServer is distributed under LGPL 3 license.
 *
 * Copyright (C) 2017 CNRS (Lab-STICC UMR CNRS 6285)
 *
 *
 * OrbisServer is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisServer is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * OrbisServer. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */


// <![CDATA[
$('#navbar').affix({
    offset: {
        top: $('header').height()
    }
});

$('#Mymodal').on('shown.bs.modal', function () {$('#sign in').focus()})

function process(){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/process",
        async: false,
        success : function(text)
        {
            $('#content').removeClass();
            $('#content').addClass('col-xs-12 col-sm-12 col-md-12');
            $('#left-nav').addClass('slide-in');
            $('#main-body').addClass('is-menu');
            $( "#content" ).html(String(text));
            processLeftNav();

        },
        error : function(text)
        {
            $('#content').removeClass();
            $('#content').addClass('col-xs-12 col-sm-12 col-md-12');
            $('#left-nav').removeClass('slide-in');
            $('#main-body').removeClass('is-menu');
            $( "#content" ).html(String(text.responseText));
        }
    });

    $("#Data").removeClass("active");
    $("#Process").addClass("active");
    $("#Share").removeClass("active");
}

function processLeftNav(){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/process/leftNavContent",
        async: false,
        success : function(text)
        {
            $( "#process-content" ).html(String(text));
            $( "#dropdown-data" ).removeClass('in');
        },
        error : function(text)
        {
            $( "#content" ).html(String(text.responseText));
        }
    });
}

function home(){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/home",
        async: false,
        success : function(text)
        {
            $( "#content" ).html(String(text));
        },
        error : function(text)
        {
            $( "#content" ).html(String(text.responseText));
        }
    });
    $('#left-nav').removeClass('slide-in');
    $('#content').removeClass();
    $('#content').addClass('col-xs-12 col-sm-12 col-md-12');
    $('#main-body').removeClass('is-menu');
    $("#Data").removeClass("active");
    $("#Process").removeClass("active");
    $("#Share").removeClass("active");
    $("#dropdown-process").removeClass("in");
    $("#dropdown-data").removeClass("in");
}

function showProcess(id){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/describeProcess",
        data: {
            "id": id
        },
        async: false,
        success : function(text)
        {
            $( "#content" ).html(String(text));
        },
        error : function(text)
        {
            $( "#content" ).html(String(text.responseText));
        }
    });
}

function showImport(id){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/describeProcess",
        data: {
            "id": id
        },
        async: false,
        success : function(text)
        {
            $( "#content_top" ).html(String(text));
        },
        error : function(text)
        {
            $( "#content_top" ).html(String(text.responseText));
        }
    });
}

function showExport(id){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/describeProcess",
        data: {
            "id": id
        },
        async: false,
        success : function(text)
        {
            $( "#content_top" ).html(String(text));
        },
        error : function(text)
        {
            $( "#content_top" ).html(String(text.responseText));
        }
    });
}

function showUser(){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/user",
        async: false,
        success : function(text)
        {
            $( "#user_ul" ).html(String(text));
            home();
        },
        error : function(text)
        {
            $( "#user_ul" ).html(String("Error"));
            home();
        }
    });
}

function signIn(){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/signIn",
        async: false,
        success : function(text)
        {
            $( "#content" ).html(String(text));
        },
        error : function(text)
        {
            $( "#content" ).html(String(text.responseText));
        }
    });
}

//** leftnav Script */
function jobs(){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/jobs",
        async: false,
        success : function(text)
        {
            $( "#list" ).html("");
            $( "#content" ).html(String(text));
        },
        error : function(text)
        {
            $( "#content" ).html(String(text.responseText));
        }
    });
}

function data(){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/data",
        async: false,
        success : function(text)
        {
            $('#left-nav').addClass('slide-in');
            $('#main-body').addClass('is-menu');
            $('#content').removeClass();
            $('#content').addClass('col-xs-12 col-sm-12 col-md-12');
            $('#content').html('<div id="row_top" class="row"><div id="content_top"></div></div><div id="row_bottom" class="row"><div id="content_bottom"></div></div>');
            $('#row_top').css('height', 'none');
            $('#content_top').removeClass();
            $('#content_bottom').removeClass();
            $('#content_top').addClass('col-sm-12 col-md-12');
            $('#row_bottom').css('height', '0%');
            $('#content_bottom').addClass('col-sm-12 col-md-12');
            $( "#content_top" ).html(String(text));
            dataLeftNav();
        },
        error : function(text)
        {
            $('#left-nav').removeClass('slide-in');
            $('#main-body').removeClass('is-menu');
            $('#content').removeClass();
            $('#content').addClass('col-xs-12 col-sm-12 col-md-12');
            $( "#content" ).html(String(text.responseText));
        }
    });
    $("#Data").addClass("active");
    $("#Process").removeClass("active");
    $("#Share").removeClass("active");
}

function dataLeftNav(){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/dataleftnav",
        async: false,
        success : function(text)
        {
            $( "#data-content" ).html(String(text));
            $( "#dropdown-process" ).removeClass('in');
        },
        error : function(text)
        {
            $( "#content" ).html(String(text.responseText));
        }
    });
}

function importData(search_id){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/data/import",
        data: {
            "filters": getFilters(search_id)
        },
        async: false,
        success : function(text)
        {
            $('#dropdown-export').removeClass('in');
            $('#left-nav').addClass('slide-in');
            $( '#import-list' ).html(String(text));
        },
        error : function(text)
        {
            $( '#content' ).html(String(text));
        }
    });
}

function exportData(search_id){
    $.ajax({ type: "GET",
        url: "http://localhost:8080/data/export",
        data: {
            "filters": getFilters(search_id)
        },
        async: false,
        success : function(text)
        {
            $('#dropdown-import').removeClass('in');
            $('#left-nav').addClass('slide-in');
            $( '#export-list').html(String(text));
        },
        error : function(text)
        {
            $( '#export-list'  ).html(String(text));
        }
    });
}

function listProcess(search_id){
    $.ajax({ type: "GET",
        data: {
            "filters": getFilters(search_id)
        },
        url: "http://localhost:8080/process/processList",
        async: false,
        success : function(text)
        {
            $('#left-nav').addClass('slide-in');
            $( '#process-list' ).html(String(text));
        },
        error : function(text)
        {
            $( '#process-list' ).html(String("Error"));
        }
    });
}

function getFilters(search_id){
    if ("undefined" === typeof search_id) {
        return '';
    }
    return $("#"+search_id).val();
}

$(".rotate").click(function(){
   $(this).toggleClass("down");
})

function nameFile(){
    var title = $('#file').val().replace(/\\/g, '/').replace(/.*\//, '');
    $('#in-browse').val(title);
}

function loadFile(id){
    nameFile();
    var form = new FormData();
    form.append("file",$("#file")[0].files[0]);
    var request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:8080/uploading', false);
    request.send(form);
    if (request.status === 200) {
        $("#"+id).addClass("has-success")
    } else {
        $("#"+id).addClass("has-error")
    }
}

/** Login modal scripts */
$(function() {
    $("#login-form").on("submit", function(e) {
        e.preventDefault();
        $.ajax({
            url: $('#login-form')[0].action,
            type: 'POST',
            data: $(this).serialize(),
            beforeSend: function() {
                $("#login-refresh").addClass('gly-spin');
                $("#login-refresh").addClass('glyphicon-refresh');
                $('#login_btn').prop("disabled", true);
            },
            success: function(data) {
                writeCookie("token",data);
                $('#loginModal').modal('hide');
                $("#login-refresh").removeClass('gly-spin');
                $("#login-refresh").removeClass('glyphicon-refresh');
                $('#login_btn').prop("disabled", false);
                $('#text-login-msg').html("Type your username and password");
                $('#login-modal-body').removeClass('has-error')
                showUser();
            },
            error: function(data) {
                $("#login-refresh").removeClass('gly-spin');
                $("#login-refresh").removeClass('glyphicon-refresh');
                $('#login_btn').prop("disabled", false);
                $('#login-modal-body').addClass('has-error')
                $('#text-login-msg').html(data.responseText);
            }
        });
    });
});

function login(){
    $('#login_btn').html('Login<i id="login-refresh" class="glyphicon">');
    $('#login_register_btn').html('Register');
    $('#login-form').attr('action','/login');
    $("#login_register_btn").attr('onClick', 'javascript:register()')
}

function register(){
    $('#login_btn').html('Register<i id="login-refresh" class="glyphicon">');
    $('#login_register_btn').html('Login');
    $('#login-form').attr('action','/register');
    $("#login_register_btn").attr('onClick', 'javascript:login()')
}

function pwdLost(){
    $('#login-footer-text').html('Please contact your administrator');
    setTimeout(hideLoginFooterText, 5000);
}

function hideLoginFooterText(){
    $('#login-footer-text').html('');
}

/** cookies functions */
function writeCookie(name, value) {
    document.cookie = name + "=" + value;
}

function readCookie(name) {
    var i, cookie, cookies, nameEq = name + "=";
    cookies = document.cookie.split(';');
    for(i=0;i<cookies.length;i++) {
        cookie = cookies[i];
        while (cookie.charAt(0)==' ') {
            cookie = cookie.substring(1,cookie.length);
        }
        if (cookie.indexOf(nameEq) == 0) {
            return cookie.substring(nameEq.length,cookie.length);
        }
    }
    return '';
}

/** User scripts */
function user_settings(){
    $.ajax({
        type: "GET",
        url: "/user/settings",
        async: false,
        success : function(text)
        {
            $( "#content" ).html(String(text));
        },
        error : function(text)
        {
            $( "#content" ).html(text.responseText);
        }
    });
}

function log_out(){
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/user/logOut",
        async: false,
        success : function(text)
        {
            $( "#user_ul" ).html(String(text));
            writeCookie("token", "");
            showUser();
        },
        error : function(text)
        {
            $( "#user_ul" ).html(String("Error"));
            showUser();
        }
    });
}

function toggleDatabaseView(){
    if ( $('#content_bottom').children().length > 0 ) {
        $('#row_top').css('height', 'none');
        $('#row_bottom').css('height', '0%');
        $( "#content_bottom" ).html("");
        $('#db_view_button').removeClass('coloring');
        $('#db_view_button').addClass('uncoloring');
    }
    else {
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/data/database",
            async: false,
            success : function(text)
            {
                $('#row_top').css('height', '50%');
                $('#row_bottom').css('height', '50%');
                $( "#content_bottom" ).html(String(text));
                $('#db_view_button').addClass('coloring');
                $('#db_view_button').removeClass('uncoloring');
            },
            error : function(text)
            {
                $( "#user_ul" ).html(String("Error"));
            }
        });
    }
}

home();
login();
showUser();
// ]]>