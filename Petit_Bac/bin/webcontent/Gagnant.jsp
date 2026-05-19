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

        <p id="message_score">votre score est de <strong><%=score%></strong> points.</p>
        
        <form action="Serv" method="get">
            <input type="hidden" name="joueur" value="<%=joueur%>">
            <button type="submit">REJOUER</button>
            <input type="hidden" name="op" value="redemarrer_partie">
        </form>
    </div>

    <script>
        const params = new URLSearchParams(window.location.search);

        if (nomGagnant) {
            document.getElementById('message_victoire').innerText = 
                "Félicitations " + nomGagnant + " !";
        }
    </script>
</body>
</html>