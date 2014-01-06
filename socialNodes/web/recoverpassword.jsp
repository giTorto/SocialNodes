<%-- 
    Document   : recoverpassword
    Created on : 6-gen-2014, 21.23.59
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
                        Hai dimenticato la password?
                    </h3>
                    <p style="padding-bottom: 0.2em">
                        Inserisci il tuo indirizzo email per ripristinare la password. Potresti dover consultare la cartella dello spam.
                    </p>
                    <form role="form">
                        <div class="form-group">
                            <label for="exampleInputEmail1">Email address</label><input type="email" class="form-control" id="exampleInputEmail1" name="email" />
                        </div>
                </div> <a style="float: right;" href="#" class="btn btn-primary">Invia <span class="glyphicon glyphicon-send"></span></a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
