<%
    int partie = (Integer) request.getAttribute("partie");
    int joueur = (Integer) request.getAttribute("joueur");
    int round = (Integer) request.getAttribute("round");
%>
<html>
<head>
    <title>Attente_Round_Suivant_Admin</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>

    <div class="box">
        <button id="btnContinuer">Continuer</button>
    </div>
    <script>
        const idPartie = "<%=partie%>";
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/ws/' + idPartie);
        
        document.getElementById('btnContinuer').onclick = function() {
            socket.send("CONTINUER");
        };

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "NEXT_ROUND_READY") {
                // L'admin va vers le servlet, ce qui va déclencher le facade.nextRound()
                window.location.href = "Serv?op=prochain_round&id_partie=<%= partie%>&joueur=<%= joueur%>&round=<%= round%>";
            }
        };
    </script>
</body>
</html>