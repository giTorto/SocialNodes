<%-- 
    Document   : main
    Created on : 3-gen-2014, 17.07.13
    Author     : Giulian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="./stile.css/main.css">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Benvenuto</h1>
        <div class="container">
            <div class="header">
                
            </div>
            <div class="content">
                <div class="sidebar">
                    <ul class="list-group">
                        <a href=afterLogged/afterLogin?op=showinviti">
                            <li align="center" class="list-group-item" style="background-color:lightgreen; text-color:white" ><h3>Inviti</h3></li>
                        </a>
                        <a href="afterLogged/afterLogin?op=showgruppi">
                            <li align="center" class="list-group-item" style="background-color:lightblue" ><h3>Gruppi</h3></li>
                        </a>
                        <a href="afterLogged/afterLogin?op=creagruppo">
                            <li align="center" class="list-group-item" style="background-color:orange"><h3>Crea gruppo</h3></li>
                        </a>
                        <a href="afterLogged/afterLogin?op=logout">
                            <li align="center" class="list-group-item" style="background-color:yellow"> <h3>Logout</h3></li>
                        </a>
                    </ul>
                </div>
                <div class="right">
                
                </div>
            </div>
            <div class="footer">
                
            </div>
        </div>
    </body>
</html>
