<%
    int partie = (Integer) request.getAttribute("partie");
    int joueur = (Integer) request.getAttribute("joueur");
%>
<html>
<head>
    <title>Attente_Invite</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <div class="box">
        <h1>ID PARTIE : <%= partie%></h1>
        <h2>En attente de lancement de la partie...</h2>
        <div class="loader"></div>
    </div>
    <script>
        const idPartie = "<%=partie%>";
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/ws/' + idPartie);

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "START") {
                window.location.href = "Serv?op=demarrer_round&id_partie=<%= partie%>&joueur=<%= joueur%>";
            }
        };
    </script>
</body>
</html>