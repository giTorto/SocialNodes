<%-- 
    Document   : moderatore
    Created on : 14-gen-2014, 12.34.31
    Author     : Lorenzo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en"><head>
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
        <style type="text/css"></style></head>
    <body>
        <form id="poster" action="/socialNodes/afterLogged/moderatoreCtrl" method="POST" style="display: none;">
            <input type="hidden" name="op" value="actionmoderatore">
        </form>

        <table>
            <tbody><tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th></th>
                </tr>
                <tr class="form">
                    <td><input type="text" name="name" value="John Doe"></td>
                    <td><input type="text" name="email" value="john@doe.com"></td>
                    <td><input type="submit" name="update" value="Update" onclick="submitForm(this);"></td>
                </tr>
                <tr class="form">
                    <td><input type="text" name="name" value="Joe Bob"></td>
                    <td><input type="text" name="email" value="joe@bob.com"></td>
                    <td><input type="submit" name="update" value="Update" onclick="submitForm(this);"></td>
                </tr>
            </tbody></table>

    </body></html>