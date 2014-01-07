<%-- 
    Document   : terminecontrollapassword
    Created on : 7-gen-2014, 17.40.10
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recupero password</title>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body style="margin: 8em; padding: 2em">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div class="page-header">
                        <h1>
                            Socialnodes: <small>recupero password</small>
                        </h1>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <h3 style="padding-bottom: 2em">
                        Ora controlla la posta su <%=request.getParameter("email_to_recover") %>
                    </h3>
                </div>
            </div>
    </body>
</html>
