<%-- 
    Document   : createAccount
    Created on : 3-gen-2014, 17.07.01
    Author     : Giulian
--%>
<jsp:useBean id="messaggioBean" class="modelDB.Message" scope="request" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./stile.css/creaElog.css">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>Create you own Account</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <h1 class="titolo-form">Crea Account</h1>
                <p> Compila questi pochi campi e potrai accedere al sistema </p>
            </div>
            <div class="content">

                <div>
                    <jsp:getProperty name="messaggioBean" property="messaggio" />
                    <form class="form-signin" method="post" action="FirstCtrl" enctype="multipart/form-data">
                        Username: <input type="text" name="username"></br>
                        E-mail: <input type="email" name="email"> </br>
                        Password: <input type="password" name="password"> </br> 
                        Scegli un avatar dal tuo pc: <input type="file" name="avatar"> </br>
                        <input type="submit" name="submit"  class="btn-submit" value="Crea Account"> 
                    </form>
                </div>

            </div>

        </div>
    </body>
</html>
