<%-- 
    Document   : createAccount
    Created on : 5-gen-2014, 10.36.53
    Author     : Giulian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body style="">

        <div class="panel panel-default">
            <div class="panel-body" align="right">
                <button style="background-color:#cbd5dd" type="button" class="btn btn-default" align="right">Home</button>
                <button style="background-color:#cbd5dd" type="button" class="btn btn-default" align="right">Logout</button>

            </div>
        </div>

        <div class="panel panel-default" margin-left="auto"
             margin-right="auto"
             margin-top="0"
             margin-bottom="0"
             padding="0">
            <div class="panel-body">
                <h2>Nome gruppo:</h2>
                <form action="" method="post" name="myform">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="input-group">

                                <input class="form-control" name="creazione_gruppo_nome" type="text" placeholder="scegli un nome">
                                <span class="input-group-btn">
                                </span>
                            </div>
                            <!-- /input-group --></div>
                        <!-- /.col-lg-6 --></div>
                    <!-- /.row -->
                    <br>
                    <h2>Invitati:</h2>
                    <p>Inserisci gli username degli utenti che vuoi invitare separati da una virgola</p>
                    <form action="/webForum/logg/gruppiSrvlt" method="post" name="">
                        <div class="row">
                            <div class="col-lg-6">
                                <div class="input-group">

                                    <input class="form-control" name="areainviti" size="50" type="text" placeholder="ad es: username1,username2">
                                    <span class="input-group-btn">

                                    </span></div>
                                <!-- /input-group --></div>
                            <!-- /.col-lg-6 --></div>
                        <!-- /.row -->
                        <br><br>
                        <a href="javascript: submitform()" class="btn btn-primary btn-lg" role="button" align="center">Crea il gruppo! »</a>

                        </div>
                        </div>

      </body>
</html>
