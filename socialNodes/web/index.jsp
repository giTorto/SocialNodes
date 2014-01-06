<%-- 
    Document   : index
    Created on : 3-gen-2014, 11.14.14
    Author     : Giulian
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelDB.Gruppo" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% ArrayList<Gruppo> g = new ArrayList<Gruppo>(); 
    g = (new Gruppo()).listaGruppiPubblici(); %>
<% Gruppo gru = null; int i =0;%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css" rel="stylesheet">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <div class="panel-body" align="right">
                    <button style="background-color:#cbd5dd" onclick="location.href='FirstCtrl?op=crea'" type="button" class="btn btn-default" align="right">Crea Account</button>
                    <button style="background-color:#cbd5dd" onclick="location.href='FirstCtrl?op=login'" type="button" class="btn btn-default" align="right">Log In</button>
                </div>
       
                <h1>Benvenuto a Social Nodes</h1>
            </div>
            <div class="content">
                <table>
                    <thead>
                        <th>
                            Nome
                    </th>
                    <th>
                        Vai al gruppo
                    </th>
                    </thead>    
                    <% while(i<g.size()){
                        gru = (Gruppo)g.get(i);%>
                    <tr>
                        <td><%=gru.getNome() %></td>
                        <td><a href="showPosts.jsp"</td>
                    </tr>
                    <%i++; } %>
                </table>
            </div>
            <div class="footer">
            </div>    
        </div>
    </body>
    
</html>
