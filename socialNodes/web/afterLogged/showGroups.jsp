<%-- 
    Document   : showGroups
    Created on : 3-gen-2014, 17.07.34
    Author     : Giulian
--%>
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
        <title>I miei gruppi</title>
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
                        <%
                                    if (user.getIsModeratore() == 1) {
                                %>
                        <li><a href="<% out.print(request.getContextPath());%>/afterLogged/afterLogin?op=tomoderatore" 
                               class="btn btn-default">Pannello di controllo per moderatore <span class="glyphicon glyphicon-pencil"></span></a></li>
                                <%}%>
                        <li><a href="afterLogged/afterLogin?op=logout" class="btn btn-default">Logout <span class="glyphicon glyphicon-log-out"></span></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!--/Socialnodes navbar-->
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                </div>
            </div>
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="col-md-4 column" style="padding-top: 5em; padding-left: 5em;">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <div class="panel panel-primary"> 
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            I miei gruppi
                                        </h3>
                                    </div>
                                    <%
                                        ArrayList<Gruppo> gruppi_miei = user.getGruppiOwn();
                                        for (Gruppo gruppo : gruppi_miei) {
                                            out.println("<div class=\"panel-body\">"
                                                    + "<a href=\"" + request.getContextPath() + "/afterLogged/groupCtrl?op=displaygroup&groupid=" + gruppo.getIdgruppo() + "\">" + gruppo.getNome() + "</a>"
                                                    + "</div>");
                                        }
                                    %>

                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            Gruppi a cui partecipo
                                        </h3>
                                    </div>
                                    <%
                                        ArrayList<Gruppo> gruppi_parte = user.getGruppiParte();
                                        for (Gruppo gruppo : gruppi_parte) {
                                            out.println("<div class=\"panel-body\">"
                                                    + "<a href=\"" + request.getContextPath() + "/afterLogged/groupCtrl?op=displaygroup&groupid=" + gruppo.getIdgruppo() + "\">" + gruppo.getNome() + "</a>"
                                                    + "</div>");
                                        }
                                    %>

                                    <!--- manca una parte per i gruppi pubblici -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>



    </body>
</html>
