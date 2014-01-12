<%-- 
    Document   : displaygroup
    Created on : 9-gen-2014, 13.07.48
    Author     : Lorenzo
--%>

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
        <style>
            html,body{
                height:100%;
                overflow:hidden;
            }
            h1{
                -webkit-margin-before: 0;
                -webkit-margin-after: 0;
                margin-top:20px;
                margin-bottom:5px;
            }

            div.msgBox{
                width:80%;
                background:#eee;
                height:10%;
                margin:1% 10% 0 10%;
            }
            div.msgBox textarea{
                resize:none;
                display:inline;
                height:100%;
                width:88%;
            }
            div.message{
                width:98%;
                background:#888;
                margin:1% 1% 1% 1%;
                color: #fff;

            }

            div.msg1{
                background-color: #39b3d7;
                border-color: #269abc;
            }

            div.msg2{
                background-color: #5cb85c;
                border-color: #4cae4c;
            }	
            div.forumWrapper{
                overflow:hidden;
                background:rgba(0,0,0,0.20);
                width:80%;
                margin:0 10% 0 10%;
                height:75%;
                overflow-y:scroll;
            }
            div.said{
                text-align:right;
                background:rgba(0,0,0,0.05);
                font-weight:bold
            }
            div.send{
                position:relative;
                float:right;
                top:25%;
                width:10%;
                height:100%;
                vertical-align:middle
            }	
            span.glyphicon{
                margin-right: 3px;
                margin-left: 2px;
            }
        </style>
    </head>

    <body>
        <div style="left:74%;position:absolute;top:1%;" >

            <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-wrench"></span>Impostazioni</button>         
            <%
                if (gruppo.getNomeOwner().equals(user.getUsername())) {
                    String output = "<a href=\"/socialNodes/afterLogged/groupCtrl/generapdf?groupid=" + gruppo.getIdgruppo()
                            + "\" class=\"btn btn-primary btn-large\">Genera PDF</a>";
                    out.println(output);
                }
            %>
            <button type="button" class="btn btn-warning"> <span class="glyphicon glyphicon-backward"></span>Indietro </button>
            <button type="button" class="btn btn-danger"> <span class="glyphicon glyphicon-log-out"></span>Logout</button>
        </div>

        <div style="text-align:center" >
            <h1>
               Forum <small> <%gruppo.getNome(); %></small>
            </h1>
        </div>
        <div class="forumWrapper panel">
            <div id="forum">
                <div class="panel message msg1"><div class="said ">Tizio ha scritto:</div>Ciao</div>
                <div class="panel message msg1"><div class="said ">Tizio ha scritto:</div>Come va?</div>
            </div></div>
        <div class="msgBox panel">
            <textarea class="form-control" id="msg" ></textarea>
            <div class="send" >
                <div class="btn-group">
                    <button type="button" class="btn btn-default" id="submit"><span class="glyphicon glyphicon-arrow-up"> </span>Send</button>
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><span class="glyphicon glyphicon-picture"> </span>Aggiungi un'immagine</li>
                    </ul>
                </div>
            </div>
        </div>

    </body>
</html>

