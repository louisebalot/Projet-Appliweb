<%
    int partie = (Integer) request.getAttribute("partie");
    int joueur = (Integer) request.getAttribute("joueur");
    int round = (Integer) request.getAttribute("round");
%>
<html>
<head>
    <title>Attente_Prochain_Round_Invite</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <div class="box">
        <h1>Veuillez patienter</h1>
        <div class="loader"></div>
    </div>
    <script>
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/ws');

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "NEXT_ROUND_READY") {
                window.location.href = "Serv?op=prochain_round&id_partie=<%= partie%>&joueur=<%= joueur%>&round=<%= round%>";
            }
        };
    </script>
</body>
</html>