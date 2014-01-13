<%-- 
    Document   : settings_group
    Created on : 13-gen-2014, 11.30.06
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="modelDB.Utente" scope="session"/>
<jsp:useBean id="gruppo" class="modelDB.Gruppo" scope="request"/>
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
                var nodes = document.getElementById("private_elements").getElementsByTagName('*');
                for (var i = 0; i < nodes.length; i++)
                {
                    nodes[i].disabled = true;
                }
                //document.getElementById('private_elements').style.visibility = 'hidden';
                document.getElementById('radios-privato').checked = false;


            }

            function showDiv() {
                var nodes = document.getElementById("private_elements").getElementsByTagName('*');
                for (var i = 0; i < nodes.length; i++)
                {
                    nodes[i].disabled = false;
                }
                //document.getElementById('private_elements').style.visibility = 'visible';
                document.getElementById('radios-pubblico').checked = false;

            }
        </script>




    </head>
    <body>
        <div class="container" style="padding: 2em; padding-bottom: 4em;" >
            <div class="row clearfix">
                <div class="col-md-2 column">
                    <button type="button" class="btn btn-primary">Indietro</button>
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
                    <form class="form-horizontal" method="POST" action="/socialNodes/afterLogged/groupCtrl?op=modificagruppo">
                        <input type="hidden" name="op" value="creagruppo">
                        <input type="hidden" name="groupid" value="<%out.print(gruppo.getIdgruppo());%>">
                        <fieldset>

                            <!-- Form Name -->
                            <legend>Modifica il tuo gruppo</legend>

                            <!-- Text input-->
                            <div class="control-group" style="padding: 1em;">
                                <label class="control-label" for="textinput_nomegruppo">Modifica il nome del tuo gruppo</label>
                                <div class="controls">
                                    <input id="textinput_nomegruppo" name="modifica_nomegruppo" type="text" placeholder="groupname" class="input-xlarge">

                                </div>
                            </div>

                            <!--Change avatar>
                            <div class="container">
                                <div class="row clearfix">
                                    <div class="col-md-12 column">
                                        <img alt="140x140" src="http://lorempixel.com/140/140/" /> <input type="file" class="btn btn-success btn-large" value="Cambia avatar"/>
                                    </div>
                                </div>
                            </div-->

                            <!-- Multiple Radios -->
                            <div class="control-group" style="padding: 1em;">
                                <label class="control-label" for="radios">Gruppo pubblico o privato?</label>
                                <div class="controls">
                                    <label class="radio" for="radios-0">
                                        <input type="radio" name="radios" id="radios-privato" value="privato" <% if (gruppo.getIsPublic() == 0) {
                                                out.print("checked=\"checked\"");
                                            };%>  onclick="javascript:showDiv()">
                                        Privato
                                    </label>
                                    <label class="radio" for="radios-1">
                                        <input type="radio" name="radios" id="radios-pubblico" value="pubblico" <% if (gruppo.getIsPublic() == 0) {
                                                out.print("checked=\"checked\"");
                                            };%> onclick="javascript:hideDiv()">
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

