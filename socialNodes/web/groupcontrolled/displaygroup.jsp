<%-- 
    Document   : displaygroup
    Created on : 9-gen-2014, 13.07.48
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Bootstrap 3, from LayoutIt!</title>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">

        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
        <link rel="shortcut icon" href="img/favicon.png">

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/scripts.js"></script>

        
        <style>
            #chatlist {
                overflow:auto; /* This will add a scroll when required */
                width:50%;
                float:bottom;
                height: 70%;	
            }
        </style>

    </head>

    <body>
        <div class="container" style="padding-top: 2em;" >
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
                                <a href="#">Inviti</a>
                            </li>
                            <li>
                                <a href="#">Crea gruppo</a>
                            </li>
                            <li class="divider">
                            <li>
                                <a href="#">Logout</a>
                            </li>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="container" style="padding-top: 5em;">
                <div class="row clearfix">
                    <div class="col-md-6 column">
                        <div class="page-header">
                            <h1>
                                Nome gruppo <small>forum</small>
                            </h1>
                        </div>
                    </div>
                    <div class="col-md-6 column" style="padding: 3em;">
                        <button type="button" class="btn btn-info btn-block btn-lg">Impostazioni gruppo</button>
                    </div>
                </div>
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <form class="form-horizontal" style="padding-left: 4em; padding-bottom: 3em;">
                            <fieldset>

                                <!-- Form Name -->


                                <!-- Textarea -->
                                <div class="control-group">
                                    <label class="control-label" for="textarea">Scrivi qui il tuo messaggio</label>
                                    <div class="controls">                     
                                        <textarea id="textarea" name="textarea" placeholder="messaggio" cols="50" ></textarea>
                                    </div>
                                </div>

                                <!-- File Button --> 
                                <div class="control-group">
                                    <label class="control-label" for="filebutton">Allegato</label>
                                    <div class="controls">
                                        <input id="filebutton" name="filebutton" class="input-file" type="file">
                                    </div>
                                </div>

                                <!-- Button (Double) -->
                                <div class="control-group">

                                    <div class="controls">
                                        <button id="button1id" name="button1id" class="btn btn-success">Invia</button>

                                    </div>
                                </div>

                            </fieldset>
                        </form>
                        <div id="chatlist">
                            <ul class="list-unstyled" >
                                <li>
                                    Lorem ipsum dolor sit amet
                                </li>
                                <li>
                                    Consectetur adipiscing elit
                                </li>
                                <li>
                                    Integer molestie lorem at massa
                                </li>
                                <li>
                                    Facilisis in pretium nisl aliquet
                                </li>
                                <li>
                                    Nulla volutpat aliquam velit
                                </li>
                                <li>
                                    Faucibus porta lacus fringilla vel
                                </li>
                                <li>
                                    Aenean sit amet erat nunc
                                </li>
                                <li>
                                    Eget porttitor lorem
                                </li>
                                <li>
                                    Consectetur adipiscing elit
                                </li>
                                <li>
                                    Integer molestie lorem at massa
                                </li>
                                <li>
                                    Facilisis in pretium nisl aliquet
                                </li>
                                <li>
                                    Nulla volutpat aliquam velit
                                </li>
                                <li>
                                    Faucibus porta lacus fringilla vel
                                </li>
                                <li>
                                    Aenean sit amet erat nunc
                                </li>
                                <li>
                                    Eget porttitor lorem
                                </li>
                                <li>
                                    Consectetur adipiscing elit
                                </li>
                                <li>
                                    Integer molestie lorem at massa
                                </li>
                                <li>
                                    Facilisis in pretium nisl aliquet
                                </li>
                                <li>
                                    Nulla volutpat aliquam velit
                                </li>
                                <li>
                                    Faucibus porta lacus fringilla vel
                                </li>
                                <li>
                                    Aenean sit amet erat nunc
                                </li>
                                <li>
                                    Eget porttitor lorem
                                </li>
                                <li>
                                    Consectetur adipiscing elit
                                </li>
                                <li>
                                    Integer molestie lorem at massa
                                </li>
                                <li>
                                    Facilisis in pretium nisl aliquet
                                </li>
                                <li>
                                    Nulla volutpat aliquam velit
                                </li>
                                <li>
                                    Faucibus porta lacus fringilla vel
                                </li>
                                <li>
                                    Aenean sit amet erat nunc
                                </li>
                                <li>
                                    Eget porttitor lorem
                                </li>
                                <li>
                                    Consectetur adipiscing elit
                                </li>
                                <li>
                                    Integer molestie lorem at massa
                                </li>
                                <li>
                                    Facilisis in pretium nisl aliquet
                                </li>
                                <li>
                                    Nulla volutpat aliquam velit
                                </li>
                                <li>
                                    Faucibus porta lacus fringilla vel
                                </li>
                                <li>
                                    Aenean sit amet erat nunc
                                </li>
                                <li>
                                    Eget porttitor lorem
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
    </body>
</html>

