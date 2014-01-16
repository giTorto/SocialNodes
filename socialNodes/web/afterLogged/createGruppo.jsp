<%-- 
    Document   : createAccount
    Created on : 5-gen-2014, 10.36.53
    Author     : Giulian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>Socialnodes: creazione gruppo</title>

        <script language=javascript type='text/javascript'>
            function hideDiv() {
                 // DOM3 = IE5, NS6 
                    document.getElementById('private_elements').style.visibility = 'hidden';
                    document.getElementById('radios-privato').checked=false;
                
                
            }

            function showDiv() {
               document.getElementById('private_elements').style.visibility = 'visible';
                    document.getElementById('radios-pubblico').checked=false;
                
            }
        </script>




    </head>
    <body>
        <div class="container" style="padding: 2em; padding-bottom: 4em;" >
            <div class="row clearfix">
                 <div class="col-md-2 column">
                    <button type="button" class="btn btn-primary" onclick="location.href='main.jsp'">Indietro</button>
                </div>
                <div class="col-md-6 column">
                    <!--qua possiamo metterci una scritta header da usare in tutte le altre pagine-->
                </div>
                <div class="col-md-4 column">
                    <div class="btn-group">
                        <button class="btn btn-primary">Azioni</button> <button data-toggle="dropdown" class="btn btn-default dropdown-toggle"><span class="caret"></span></button>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="afterLogin?op=showinviti">Inviti</a>
                            </li>
                            <li>
                                <a href="afterLogin?op=tocreation">Crea gruppo</a>
                            </li>
                            <li class="divider">
                            <li>
                                <a href="afterLogin?op=logout">Logout</a>
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
                    <form class="form-horizontal" method="POST" action="/socialNodes/afterLogged/groupCtrl">
                        <input type="hidden" name="op" value="creagruppo">
                        <fieldset>

                            <!-- Form Name -->
                            <legend>Creazione nuovo gruppo</legend>

                            <!-- Text input-->
                            <div class="control-group" style="padding: 1em;">
                                <label class="control-label" for="textinput_nomegruppo">Inserire il nome del nuovo gruppo</label>
                                <div class="controls">
                                    <input id="textinput_nomegruppo" name="creazione_gruppo_nome" type="text" placeholder="groupname" class="input-xlarge">

                                </div>
                            </div>

                            <!-- Multiple Radios -->
                            <div class="control-group" style="padding: 1em;">
                                <label class="control-label" for="radios">Gruppo pubblico o privato?</label>
                                <div class="controls">
                                    <label class="radio" for="radios-0">
                                        <input type="radio" name="radios" id="radios-privato" value="privato" checked="checked" onclick="javascript:showDiv()">
                                        Privato
                                    </label>
                                    <label class="radio" for="radios-1">
                                        <input type="radio" name="radios" id="radios-pubblico" value="pubblico" onclick="javascript:hideDiv()">
                                        Pubblico
                                    </label>
                                </div>
                            </div>

                            <!-- Text input-->
                            <div class="row" style="padding: 1em;" id="private_elements">
                                <div class="col-lg-6">
                                    <label>Inserisci gli username degli utenti che vuoi invitare separati da una virgola</label>
                                    <div class="input-group">

                                        <input class="form-control" name="areainviti" size="50" type="text" placeholder="ad es: username1,username2">
                                        <span class="input-group-btn">

                                        </span></div>
                                    </div>
                            </div>

                            <!-- Button -->
                            <div class="control-group" style="padding: 1em;">
                                <label class="control-label" for="singlebutton"></label>
                                <div class="controls">
                                    <button type="submit" id="singlebutton" name="singlebutton" class="btn btn-primary">Fatto!</button>
                                </div>
                            </div>

                        </fieldset>
                    </form>


                </div>
            </div>
        </div>

    </body>

