<%@ page import="pack.Joueur" %>
<%@ page import="pack.Partie" %>
<%
    Partie partie = (Partie) request.getAttribute("partie");
    Joueur joueur = (Joueur) request.getAttribute("joueur");
%>
<html>
<head>
    <title>Attente_Invite</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <div class="box">
        <h1>ID PARTIE : <%= partie.getId() %></h1>
        <h2>En attente de lancement de la partie...</h2>
        <div class="loader"></div>
    </div>
    <script>
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/Serv');

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "START") {
                window.location.href = "Serv?op=demarrer_round&id_partie=<%= partie.getId() %>&joueur=<%= joueur.getId() %>";
            }
        };
    </script>
</body>
</html>