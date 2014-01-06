<%-- 
    Document   : logIn
    Created on : 3-gen-2014, 17.06.44
    Author     : Giulian
--%>
<jsp:useBean id="messaggioBean" class="modelDB.Message" scope="request" />
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><!---- questa parte poi andrà messa in un css a parte-->
        <link rel="stylesheet" type="text/css" href="./stile.css/creaElog.css">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
      
            


                <div class="custom panel" >
                    <form class="form-signin" method ="post" action="FirstCtrl">
                        <h2 class="titolo-form" >Social Nodes Log In</h2>
                        <jsp:getProperty name="messaggioBean" property="messaggio" />
                         <jsp:getProperty name="messaggioBean" property="value" />
                        <input type="hidden" name="op" value="login">
                        <input type="text" name="email" class="form-control" placeholder="Email address" required="" autofocus="" >
                        <input type="password" name="password" class="form-control" placeholder="Password" required="">
                        <button class="btn-submit" type="submit">Sign in</button>
                    </form>
                </div>
                
        
            
   
    </body>
</html>
