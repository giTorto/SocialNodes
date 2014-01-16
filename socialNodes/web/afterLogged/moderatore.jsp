<%-- 
    Document   : moderatore
    Created on : 14-gen-2014, 12.34.31
    Author     : Lorenzo
--%>

<%@page import="modelDB.Gruppo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="modelDB.Utente" scope="session"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
            <title>Form and Table Row Nesting Workaround</title>
            <script type="text/javascript">
                function submitForm(element)
                {
                    element.type = 'hidden';

                    while (element.className != 'form')
                        element = element.parentNode;

                    var form = document.getElementById('poster');

                    var inputs = element.getElementsByTagName('input');
                    while (inputs.length > 0)
                        form.appendChild(inputs[0]);

                    var selects = element.getElementsByTagName('select');
                    while (selects.length > 0)
                        form.appendChild(selects[0]);

                    var textareas = element.getElementsByTagName('textarea');
                    while (textareas.length > 0)
                        form.appendChild(textareas[0]);

                    form.submit();
                }
            </script>
            <style type="text/css"></style>
    </head>
    <body>
        <form id="poster" action="/socialNodes/afterLogged/moderatoreCtrl" method="POST" style="display: none;">
            <input type="hidden" name="op" value="actionmoderatore">
        </form>

        <div class="container">
            <div class="row clearfix">
                 <div class="col-md-2 column">
                    <button type="button" class="btn btn-primary" onclick="location.href='main.jsp'">Indietro</button>
                </div>
                <div class="col-md-12 column">
                    <div class="page-header">
                        <h1>
                            Moderatore: <small>pannello di controllo</small>
                        </h1>
                    </div>
                    <h3>
                        Tutti i gruppi, attivare/bloccare un gruppo alla volta!
                    </h3>
                    <table class="table">
                        <tbody>
                            <tr>
                                <th>Nome gruppo</th>
                                <th># partecipanti</th>
                                <th>Pubblico/Privato</th>
                                <th># post</th>
                                <th>Attività gruppo: on/off</th>
                                <th>Update</th>
                            </tr>
                            <%!
                                ArrayList<Gruppo> allGruppi = null;
                                                                                                                                                                                                                                                                                            %>          
                            <%
                                allGruppi = user.getAllGruppi();
                            %>
                            <%
                                for (Gruppo gruppo : allGruppi) {
                            %>
                            <tr class="form" style="background: ">
                                <td>
                                    <input type="hidden" name="nome_gruppo" value="<%=gruppo.getNome()%>">
                                    <%=gruppo.getNome()%>
                                </td>
                                <td>
                                    <%=gruppo.getData_creazione()/*da mettere la funz giusta*/%>
                                </td>
                                <td>
                                    <%if (gruppo.getIsPublic() == 0) {
                                            out.print("Privato");
                                        } else {
                                            out.print("Pubblico");
                                        }%>
                                </td>
                                <td>
                                    <%=gruppo.getNumPost()%>
                                </td>
                                <td>
                                    <div style="padding: 0.1em; padding-left: 4em;">
                                        <input type="checkbox" name="isAttivo" id="checkboxes-0" value="<%=gruppo.getIsPublic()%>"><!--occio va messa la funz giusta getisactive()-->
                                    </div>

                                </td>
                                <td>
                                    <input class="btn btn-success" type="submit" name="update" value="Update" onclick="submitForm(this);">
                                </td>
                            </tr>
                            <%
                                }
                            %>
                            <!--tr class="form">
                                <td><input type="text" name="name" value="John Doe"></td>
                                <td><input type="text" name="email" value="john@doe.com"></td>
                                <td><input type="submit" name="update" value="Update" onclick="submitForm(this);"></td>
                            </tr>
                            <tr class="form">
                                <td><input type="text" name="name" value="Joe Bob"></td>
                                <td><input type="text" name="email" value="joe@bob.com"></td>
                                <td><input type="submit" name="update" value="Update" onclick="submitForm(this);"></td>
                            </tr-->
                        </tbody>
                    </table>
                </div>
            </div>
        </div>



    </body></html>