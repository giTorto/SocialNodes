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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Change the World!</h1>
        <table>
        <% while(i<g.size()){
            gru = (Gruppo)g.get(i);%>
            <tr>
                <td><%=gru.getNome() %></td>
            </tr>
        <%i++; } %>
        </table>
    </body>
</html>
