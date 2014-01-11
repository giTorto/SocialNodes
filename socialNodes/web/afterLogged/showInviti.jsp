<%-- 
    Document   : showInviti
    Created on : 5-gen-2014, 10.37.13
    Author     : Giulian
--%>
<jsp:useBean id="user" type="modelDB.Utente" scope="session" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>Socialnodes: gestione inviti</title>
    </head>
    <body background="./stile.css/images/nuvole_blu.jpg">
        <div class="container" style="padding: 2em; padding-bottom: 4em;" >
            <div class="row clearfix">
                <div class="col-md-2 column">
                    <button type="button" class="btn btn-primary">Indietro</button>
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
                                <a href="afterLogged/afterLogin?op=logout">Logout</a>
                            </li>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="page-header" style="padding-bottom: 2em;">
                        <h1>
                            Gestione inviti: <small>controlla e seleziona gli inviti che vuoi accettare</small>
                        </h1>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <h3>
                                Inviti pendenti
                            </h3>
                            <form action="#" method="POST">
                                <div style="padding: 2em;">
                                    <table class="table" >
                                        <thead>
                                            <tr class="success"> 
                                                <th>
                                                    Owner
                                                </th>
                                                <th>
                                                    Nome Gruppo 
                                                </th> 
                                                <th>
                                                    Data creazione
                                                </th> 
                                                <th>
                                                    Spunta la casella per accettare l'invito 
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
                                                <td>
                                                    01/04/2012
                                                </td>
                                                <td>
                                                    <input name="nome gruppo"
                                                           type="checkbox" value="Accetto" checked="checked"/>
                                                </td>
                                            </tr>
                                            <tr class>
                                                <td>
                                                    1
                                                </td>
                                                <td>
                                                    TB - Monthly
                                                </td>
                                                <td>
                                                    01/04/2012
                                                </td>
                                                <td>
                                                    <input name="nome gruppo"
                                                           type="checkbox" value="Accetto" checked="checked"/>
                                                </td>
                                            </tr>

                                        </tbody>
                                    </table> 
                                </div>
                                <button type="submit" class="btn btn-primary btn-block">Processa inviti</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
