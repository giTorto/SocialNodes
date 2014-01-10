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
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>Registrazione a Socialnodes</title>
    </head>
    <body>

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="page-header">
                        <h1>
                            Socialnodes: <small>Registrazione</small>
                        </h1>
                    </div>

                    <p>
                        Per registrarsi è necessario compilare il modulo sottostante
                    </p>
                    <p>
                    <h3 style="color: red"> <jsp:getProperty name="messaggioBean" property="messaggio"/> </h3>
                       
                    </p>

                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" action="FirstCtrl" method ="post"  enctype="multipart/form-data" >
                        <div class="form-group">
                            <label for="exampleInputUsername1">Username</label><input name="username" type="text" class="form-control" id="exampleInputUsername1" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">Email address</label><input name="email" type="email" class="form-control" id="exampleInputEmail1" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Password</label><input name="password" type="password" class="form-control" id="exampleInputPassword1" />
                        </div>
                        <div class="form-group">
                            <label for="exampleInputFile">Avatar</label><input name="avatar" type="file" id="exampleInputFile" />
                        </div>
                        <button style="float: right" type="submit" class="btn btn-primary">OK <span class="glyphicon glyphicon-ok"></span></button>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>


