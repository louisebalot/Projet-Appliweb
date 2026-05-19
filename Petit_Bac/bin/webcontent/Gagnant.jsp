<% int joueur = (Integer) request.getAttribute("joueur");
    int partie = (Integer) request.getAttribute("partie");
    int id_vainqueur = (Integer) request.getAttribute("id_vainqueur");
    int score = (Integer) request.getAttribute("score");
%>
<html>
<head>
    <title>Page de victoire</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>

    <div class="box">
        <h1 id="message_victoire">Calcul des scores</h1>

        <p id="message_score">Votre score : <strong><%=score%></strong> points.</p>
        
        <div id="zone-classement">
            <h2 class="titre-encadre">Classement</h2>
            
            <p id="attente-ws">En attente des scores...</p>
            
            <ul id="liste-scores"></ul>
        </div>

        <br>
        
        <form action="Serv" method="get">
            <input type="hidden" name="joueur" value="<%=joueur%>">
            <button type="submit">REJOUER</button>
            <input type="hidden" name="op" value="redemarrer_partie">
        </form>
    </div>

<script>
        const idPartie = "<%=partie%>";
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/ws/' + idPartie);

        socket.onopen = function(event) {
            console.log("Connexion WebSocket établie. Demande des scores...");
            socket.send("DEMANDER_SCORES");
        };
        
        socket.onmessage = function(event) {
            try {
                const data = JSON.parse(event.data);
                
                if (data.status === "END_GAME") {
                    let listScores = data.scores; 

                    listScores.sort((a, b) => b.score - a.score);

                    if (listScores.length > 0) {
                        document.getElementById('message_victoire').innerText = 
                            "Vainqueur : " + listScores[0].pseudo + " !";
                    }

                    const ul = document.getElementById("liste-scores");
                    ul.innerHTML = ""; 

                    listScores.forEach(j => {
                        let li = document.createElement("li");
                        li.innerHTML = `<strong>${j.pseudo}</strong> : ${j.score} pts`;
                        ul.appendChild(li);
                    });

                    document.getElementById("attente-ws").style.display = "none";
                }
            } catch (error) {
                console.error("Erreur lors de la réception du JSON :", error);
            }
        };
    </script>
</body>
</html>