<%-- 
    Document   : showPersonalSettings
    Created on : 13-gen-2014, 19.18.24
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="modelDB.Utente" scope="session"/>
<jsp:useBean id="messaggioBean" class="modelDB.Message" scope="request"/>
<!DOCTYPE html>
<html><head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>Impostazioni personali</title>
        <style type="text/css"></style></head>
    <body style="">

        <div class="container">
            <div class="row clearfix">
                <div class="col-md-2 column">
                    <button type="button" class="btn btn-primary" onclick="location.href = 'main.jsp'">Indietro</button>
                </div>
                <div class="col-md-12 column">
                    <div class="page-header">
                        <h1>
                            Socialnodes: <small>impostazioni personali</small>
                        </h1>
                    </div>

                    <p>
                        Usa il modulo sottostante per cambiare le tue impostazioni
                    </p>
                    <p>
                    </p><h3 style="color: red">  
                        <c:set var="messaggio" scope="page" value="<%=messaggioBean.getMessaggio()%>" />
                        <c:if test="${messaggio != null}">
                            <c:out value="${messaggioBean.messaggio}"/> 
                        </c:if>
                    </h3>

                    <p></p>

                </div>
            </div>
            <div class="row clearfix">


                <div class="col-md-12 column">
                    <form role="form" action="/socialNodes/afterLogged/afterLogin" method="post" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="exampleInputUsername1">Nuovo Username</label><input name="new_username" value="<%= user.getUsername()%>" type="text" class="form-control" id="exampleInputUsername1">
                        </div>                        
                        <div class="form-group">
                            <label for="exampleInputPassword1">Nuova  Password</label><input name="new_password" type="password" placeholder="se non scrivi niente la tua password resterà invariata" class="form-control" id="exampleInputPassword1">
                        </div>
                        <div class="form-group">

                            <img src="<%= user.getAvatar_link()%>" style="width: 70px; height:70px " >
                            <label for="exampleInputFile">Cambia Avatar</label><input name="new_avatar" type="file" id="exampleInputFile">
                        </div>
                        <button style="float: right" type="submit" class="btn btn-primary">OK <span class="glyphicon glyphicon-ok"></span></button>
                    </form>
                </div>
            </div>
        </div>





    </body></html>
