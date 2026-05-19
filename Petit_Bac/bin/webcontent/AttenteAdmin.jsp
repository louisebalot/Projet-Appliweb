<%
    int partie = (Integer) request.getAttribute("partie");
    int joueur = (Integer) request.getAttribute("joueur");
%>
<html>
<head>
    <title>Attente_Admin</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>

    <div class="box">
        <h1>Configuration termin&eacute;e</h1>
        <button id="btnLancer">Lancer la partie</button>
    </div>
    <script>
        const idPartie = "<%=partie%>";
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/ws/' + idPartie);

        document.getElementById('btnLancer').onclick = function() {
            socket.send("LANCER_PARTIE");
        };

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "START") {
                window.location.href = "Serv?op=demarrer_round&id_partie=<%= partie%>&joueur=<%= joueur%>";
            }
        };
    </script>
</body>
</html>