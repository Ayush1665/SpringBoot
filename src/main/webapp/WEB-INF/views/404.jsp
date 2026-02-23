<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Page Not Found</title>
    <style>
        body { text-align: center; padding: 50px; font-family: sans-serif; }
        h1 { font-size: 50px; color: #d9534f; }
        p { font-size: 20px; }
        a { text-decoration: none; color: blue; }
    </style>
</head>
<body>
    <h1>Error 404</h1>
    
    <% if(request.getAttribute("message") != null) { %>
        <p style="color: red;">Reason: <%= request.getAttribute("message") %></p>
    <% } %>

    <br>
    <a href="/abc/employees">Go back to Main</a>
</body>
</html>