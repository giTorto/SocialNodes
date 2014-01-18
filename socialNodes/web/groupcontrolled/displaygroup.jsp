<%-- 
    Document   : displaygroup
    Created on : 9-gen-2014, 13.07.48
    Author     : Lorenzo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="modelDB.Utente"%>
<%@page import="modelDB.Gruppo"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="modelDB.Utente" scope="session"/>
<jsp:useBean id="gruppo" class="modelDB.Gruppo" scope="request"/>

<!DOCTYPE html>
<html>
    <head>
        <title>Socialnodes: gruppo</title>

        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <link href="../stile.css/displaygroup.css" rel="stylesheet" type="text/css">

    </head>

    <body>
        <div style="left:74%;position:absolute;top:1%;" ><!---- allora questo if bruttissimo va eliminato -->



            <a href="/socialNodes/afterLogged/afterLogin?op=showgroups" class="btn btn-primary"><span class="glyphicon glyphicon-chevron-left"></span> Indietro</a>
            <a href="/socialNodes/afterLogged/afterLogin?op=logout" class="btn btn-primary"><span class="glyphicon glyphicon-new-window"></span> Logout</a>

        </div>

        <div style="text-align:center" >
            <h1>
                Forum <small> <jsp:getProperty name="gruppo" property="nome" /> </small>
            </h1>
        </div>
        <div class="forumWrapper panel">
            <c:forEach items="${lista_post}" var="post">
                <div class="panel message msg1">
                    <div class="said ">    
                        <c:out value="${post.writer.username}" />  alle <c:out value="${post.data_ora}" />
                        <c:out value="${post.writer.avatar_link}"/>

                    </div>
                    <c:out value="${post.testo}"/></br>
                    <b>File</b>:<a href="<c:url value="${post.link}"/>"> <c:out value="${post.realname}"/>  </a>
                </div>
            </c:forEach>
        </div></div>
    <div class="msgBox panel">
        <form action="groupCtrl" method ="post"  enctype="multipart/form-data">
            <textarea class="form-control" name="messaggio" ></textarea>
            <div class="send" >
                <div class="btn-group">
                    <input type="hidden" name="idgruppo" value="<jsp:getProperty name="gruppo" property="idgruppo"></jsp:getProperty>" >
                    <input class="btn btn-default" name="submit" type="submit" value="send">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><input  type="file" name="file"> </li>

                    </ul>
                </div>
        </form>
    </div>
</div>

</body>
</html>

