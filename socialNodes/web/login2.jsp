<%-- 
    Document   : login2
    Created on : 6-gen-2014, 16.13.26
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="UTF-8" />
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
        <title>Login and Registration Form with HTML5 and CSS3</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <meta name="description" content="Login and Registration Form with HTML5 and CSS3" />
        <meta name="keywords" content="html5, css3, form, switch, animation, :target, pseudo-class" />
        <meta name="author" content="Codrops" />
        <link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="./stile.css/demo.css" />
        <link rel="stylesheet" type="text/css" href="./stile.css/style2.css" />
        <link rel="stylesheet" type="text/css" href="./stile.css/animate-custom.css" />
    </head>
    <body>
        <div class="container">

            <header>
                <h1>SocialNodes</h1>

            </header>
            <section>				
                <div id="container_demo" >

                    <a class="hiddenanchor" id="toregister"></a>
                    <a class="hiddenanchor" id="tologin"></a>
                    <div id="wrapper">
                        <div id="login" class="animate form">
                            <form  method ="post" action="FirstCtrl" autocomplete="on"> 
                                <input type="hidden" name="op" value="login">
                                <h1>Log in</h1>
                                <p style="font-size: 28px; color: #066A75; font-family: 'FranchiseRegular'; text-align: center;">
                                    <jsp:getProperty name="messaggioBean" property="messaggio" />
                                    <jsp:getProperty name="messaggioBean" property="value" />
                                </p>
                                
                                <p> 
                                    <label for="useremail" class="uname" data-icon="u" > La tua email</label>
                                    <input id="useremail" name="email" required="required" type="text" placeholder="email"/>
                                </p>
                                <p> 
                                    <label for="password" class="youpasswd" data-icon="p"> La tua password</label>
                                    <input id="password" name="password" required="required" type="password" placeholder="eg. X8df!90EO" /> 
                                </p>
                                <p class="login button">
                                    <input type="submit" value="Login" /> 
                                </p>
                                <p class="change_link">
                                    <a href="<%=request.getServletContext().getContextPath()%>/FirstCtrl?op=recoverpassword">Dimenticato la password?</a>
                                    <a href="#toregister" class="to_register">Registrati</a>
                                </p>
                            </form>
                        </div>

                        <div id="register" class="animate form">
                            <form  method="post" action="FirstCtrl" autocomplete="on" enctype="multipart/form-data">
                                <input type="hidden" value="crea" name="op">
                                <h1> Registrazione </h1> 
                                <p> 
                                    <label for="usernamesignup" class="uname" data-icon="u">Il tuo soprannome</label>
                                    <input id="usernamesignup" name="username" required="required" type="text" placeholder="soprannome" />
                                </p>
                                <p> 
                                    <label for="emailsignup" class="youmail" data-icon="e" > La tua email</label>
                                    <input id="emailsignup" name="email" required="required" type="email" placeholder="email"/> 
                                </p>
                                <p> 
                                    <label for="passwordsignup" class="youpasswd" data-icon="p">La tua password </label>
                                    <input id="passwordsignup" name="password" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p> 
                                    <label for="passwordsignup_confirm" class="youpasswd" data-icon="p">Per favore, inserisci nuovamente la password </label>
                                    <input id="password_confirm" name="password_confirm" required="required" type="password" placeholder="eg. X8df!90EO"/>
                                </p>
                                <p>
                                    <label >Seleziona il tuo avatar </label>
                                    <input type="file" name="avatar">
                                </p>
                                <p class="signin button"> 
                                    <input type="submit" value="OK"/> 
                                </p>
                                <p class="change_link">  
                                    Sei già registrato?
                                    <a href="#tologin" class="to_register"> Vai al login </a>
                                </p>
                            </form>
                        </div>

                    </div>
                </div>  
            </section>
        </div>
    </body>
</html>
