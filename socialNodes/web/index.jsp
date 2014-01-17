
<%-- 
    Document   : index
    Created on : 3-gen-2014, 11.14.14
    Author     : Giulian
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="modelDB.Gruppo" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="messaggioBean" class="modelDB.Message" scope="request"/>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>Socialnode</title>

    </head>
    <body background="./stile.css/images/nuvole_blu.jpg">
        <!--div class="container">
            <div class="header">
                <div class="panel-body" align="right">
                    <button onclick="location.href = 'FirstCtrl?op=gotocrea'" type="button" class="btn btn-primary" align="right">Crea Account</button>
                    <button onclick="location.href = 'FirstCtrl?op=gotologin'" type="button" class="btn btn-primary" align="right">Log In</button>
                    <button onclick="location.href = 'FirstCtrl?op=gotologin2'" type="button" class="btn btn-primary" align="right">Log In2</button>
                </div-->
        <div class="container">
            <div style="padding: 4em;" class="row clearfix">
                <div class="col-md-8 column">
                    <div class="jumbotron">
                        <h1>
                            Socialnodes
                        </h1>
                        <p>
                            Per scambiare idee sugli argomenti trattati a lezione e condividere le soluzioni degli esercizi più difficili!
                        </p>
                        
                        <jsp:getProperty name="messaggioBean" property="messaggio" />
                        <p>

                        </p>
                        <p>
                            <a class="btn btn-primary btn-large" onclick="location.href = 'FirstCtrl?op=gotocrea'" 
                               align="right" style="float: right">Registrati<span class="glyphicon glyphicon-user"></span></a>
                        </p>
                    </div>
                </div>
                <div class="col-md-4 column" >
                    <div class="panel panel-default">
                        <h3 style="margin: 0.4em;">Login</h3>
                        <form style="padding: 1em;" role="form" method="post" action="FirstCtrl">
                            <input type="hidden" name="op" value="login">

                            <div class="form-group">
                                <label for="exampleInputEmail1">Email address</label><input name="email" type="email" class="form-control" id="exampleInputEmail1" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Password</label><input name="password" type="password" class="form-control" id="exampleInputPassword1" />
                            </div>
                            <a href="/socialNodes/FirstCtrl?op=gotorecoverpassword">Dimenticato la password?</a>
                            <button   type="submit" class="btn btn-primary">Log in</button>                                                                     
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">

                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                Gruppi pubblici
                            </h3>
                        </div>
                        <div class="panel-body">
                            <table class="table">
                                <thead>
                                    <tr>

                                        <th>
                                            Nome Gruppo
                                        </th>
                                        <th>
                                            # di post
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${public_groups}" var="gruppi">
                                        <tr>
                                            <td>
                                                <a href="/socialNodes/afterLogged/groupCtrl?op=displaygroup&groupid=<c:out value="${gruppi.idgruppo}" />&ut=public"> <c:out value="${gruppi.nome}"/> </a>
                                            </td>
                                            <td>
                                                <c:out value="${gruppi.numPost}" />
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </div>

                </div>
            </div>
        </div>
    </body>

</html>

