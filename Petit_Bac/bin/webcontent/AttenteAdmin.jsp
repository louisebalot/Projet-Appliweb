<%@ page import="pack.Joueur" %>
<%@ page import="pack.Partie" %>
<%
    Partie partie = (Partie) request.getAttribute("partie");
    Joueur joueur = (Joueur) request.getAttribute("joueur");
%>
<html>
<head>
    <title>Attente_Admin</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>

    <div class="box">
        <h1>Configuration terminée</h1>
        <button id="btnLancer">Lancer la partie</button>
    </div>
    <script>
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/Serv');

        document.getElementById('btnLancer').onclick = function() {
            socket.send(JSON.stringify({ action: "LANCER_PARTIE" }));
        };

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "START") {
                window.location.href = "Serv?op=demarrer_round&id_partie=<%= partie.getId() %>&joueur=<%= joueur.getId() %>";
            }
        };
    </script>
</body>
</html>