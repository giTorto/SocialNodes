<%-- 
    Document   : main
    Created on : 3-gen-2014, 17.07.13
    Author     : Giulian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="modelDB.Message" %>
<%@page import="modelDB.Utente"%>
<%@page import="modelDB.Gruppo"%>
<%@page import="java.util.ArrayList"%>
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



        <title>Socialnodes: homepage</title>
    </head>
    <body background="./stile.css/images/nuvole_blu.jpg">

        <!--Socialnodes navbar-->
        <div class="navbar navbar-default" role="navigation">
            <div class="container">
                
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Socialnodes</a>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="/socialNodes/afterLogged/afterLogin?op=showgroups" class="btn btn-default">I miei gruppi <span class="glyphicon glyphicon-th"></span></a></li>
                        <li><a href="/socialNodes/afterLogged/afterLogin?op=tocreation" class="btn btn-default">Crea gruppo <span class="glyphicon glyphicon-plus"></span></a></li>
                        <li><a href="/socialNodes/afterLogged/afterLogin?op=showinviti" class="btn btn-default">Inviti <span class="glyphicon glyphicon-user"></span></a></li>
                        <li><a href="/socialNodes/afterLogged/afterLogin?op=topersonalsettings" class="btn btn-default">Impostazioni personali <span class="glyphicon glyphicon-cog"></span></a></li>
                               
                        <c:set var="ismodera" scope="session" value="<%= user.getIsModeratore() %>" />
                                <c:if test="${ismodera == 1}">
                                        <li><a href="socialNodes/afterLogged/afterLogin?op=tomoderatore" 
                               class="btn btn-default">Pannello di controllo per moderatore <span class="glyphicon glyphicon-pencil"></span></a></li>
                                </c:if>  
                               
                               <img src="<c:url value="${user.avatar_link}" />" >
                        <li><a href="/socialNodes/afterLogged/afterLogin?op=logout" class="btn btn-default">Logout <span class="glyphicon glyphicon-log-out"></span></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!--/Socialnodes navbar-->

        <div class="container" style="padding: 5em;">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <p>
                            <h2>
                                <jsp:getProperty name="messaggio_main" property="messaggio" />
                              
                               
                                  
                                        
                                   
                               
                                
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
                                    <!--Qua ci vuole un getinvitirecenti, con foreach per -->
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
                    </div>
                </div>
            </div>
        </div>
    </div>




</body>
</html>
