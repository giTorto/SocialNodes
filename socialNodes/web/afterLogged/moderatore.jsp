<%-- 
    Document   : moderatore
    Created on : 14-gen-2014, 12.34.31
    Author     : Lorenzo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <title>Pannelo di controllo</title>
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
                    <button type="button" class="btn btn-primary" onclick="location.href = 'main.jsp'">Indietro</button>
                </div>
                <div class="col-md-12 column">
                    <div class="page-header">
                        <h1>
                            Moderatore: <small>pannello di controllo</small>
                        </h1>
                    </div>
                    <h3>
                        Tutti i gruppi, attivare/bloccare un gruppo alla volta! <small> la casella con un segno di spunta indica che il gruppo è attivo</small>
                    </h3>
                    <table class="table" id="tablemoderatore">
                        <thead>
                            <tr>
                                <th>Nome gruppo</th>
                                <th># partecipanti</th>
                                <th>Pubblico/Privato</th>
                                <th># post</th>
                                <th>Attività gruppo: on/off</th>
                                <th>Update</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set var="allgruppi" scope="page" value="<%= user.getAllGruppi()%>" />
                            <c:forEach items="${allgruppi}" var="gruppo">


                                <tr class="form" style="background: ">
                                    <td>
                                        <input type="hidden" name="groupid" value="<c:out value="${gruppo.getIdgruppo()}" />">
                                            <c:out value="${gruppo.nome}" />
                                    </td>
                                    <td>
                                        <!--Qua dovremmo pubblicare il numero di partecipanti di un gruppo-->
                                        <c:out value="${gruppo.getNumPartecipanti()}" />
                                    </td>
                                    <td>
                                        <c:set var="ispublic" scope="page" value="${gruppo.isPublic}" />
                                        <c:choose>
                                            <c:when test="${ispublic==0}">
                                                <c:out value="Privato" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="Pubblico" />
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:out value="${gruppo.numPost}" />
                                    </td>
                                    <td>
                                        <div style="padding: 0.1em; padding-left: 4em;">
                                            <c:choose>
                                                <c:when test="${gruppo.isAttivo==1}">
                                                    <input type="checkbox" name="isAttivo" id="checkboxes-0" value=1 checked>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input type="checkbox" name="isAttivo" id="checkboxes-0" value=1 >
                                                        </c:otherwise>
                                                    </c:choose>
                                                    </div>
                                                    </td>
                                                    <td>
                                                        <input class="btn btn-success" type="submit" name="update" value="Update" onclick="submitForm(this);">
                                                    </td>
                                                    </tr>
                                                </c:forEach>

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
                            
                                                <script src="https://dl.dropboxusercontent.com/u/43318531/jquery.dataTables.js"></script>
                                                <script src="https://dl.dropboxusercontent.com/u/43318531/jquery.dataTables.min.js"></script>
                                                <script src="https://dl.dropboxusercontent.com/u/43318531/jquery.js"></script>

                                                <script>
                                                            $(document).ready(function() {
                                                                $("#tablemoderatore").dataTable();
                                                            });
                                                </script>
                                                </body>
                                                </html>