<%-- 
    Document   : main
    Created on : 3-gen-2014, 17.07.13
    Author     : Giulian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="messaggio_main" class="modelDB.Message" scope="session"/>
<jsp:useBean id="user" class="modelDB.Utente" scope="session"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body background="./stile.css/images/nuvole_blu.jpg">


        <div class="container" style="padding-top: 2em;">
            <div class="row clearfix">
                <div class="col-md-2 column">
                    <button type="button" class="btn btn-primary">Home</button>
                </div>
                <div class="col-md-6 column">
                    <!--qua possiamo metterci una scritta header da usare in tutte le altre pagine-->
                </div>
                <div class="col-md-4 column">
                    <div class="btn-group">
                        <button class="btn btn-primary">Azioni</button> <button data-toggle="dropdown" class="btn btn-default dropdown-toggle"><span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="afterLogged/afterLogin?op=showinviti">Inviti</a>
                            </li>
                            <li>
                                <a href="afterLogged/afterLogin?op=tocreation">Crea gruppo</a>
                            </li>
                            <li class="divider">
                            <li>
                                <a href="afterLogged/afterLogin?op=logout">logout</a>
                            </li>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row clearfix" style="padding-top: 5em">
                <div class="col-md-8 column">
                    <p>
                    <h2>
                        <jsp:getProperty name="messaggio_main" property="messaggio" />
                        <% user.getNews();
                        %>
                    </h2>
                    </p>
                    <div class="page-header">
                        <h1>
                            Notifiche dai gruppi <small></small>
                        </h1>
                    </div>
                    <table class="table" style="background: whitesmoke">
                        <thead>
                            <tr class="panel panel-primary" style="background-color: #006DCC; color: white">

                                <th>
                                    Gruppo
                                </th>
                                <th>
                                    Notifica
                                </th>

                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    1
                                </td>
                                <td>
                                    TB - Monthly
                                </td>

                            </tr>

                        </tbody>
                    </table>
                    <div class="page-header">
                        <h1>
                            Nuovi inviti <small></small>
                        </h1>
                    </div>
                    <table class="table" style="background: whitesmoke">
                        <thead>
                            <tr class="panel panel-primary" style="background-color: #006DCC; color: white">
                                <th>
                                    Gruppo
                                </th>
                                <th>
                                    Invitato da
                                </th>
                                <th>
                                    Accetti?
                                </th>

                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    1
                                </td>
                                <td>
                                    TB - Monthly
                                </td>

                            </tr>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-4 column" style="padding-top: 5em; padding-left: 5em;">
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                Panel title
                            </h3>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>

                    </div>
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                Panel title
                            </h3>
                        </div>
                        <div class="panel-body">
                            Panel content
                        </div>
                        <div class="panel-footer">
                            Panel footer
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
