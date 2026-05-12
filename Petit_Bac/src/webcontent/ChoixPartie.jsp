<html>
<head>
    <meta charset="UTF-8">
    <title>ChoixPartie</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <% int joueur = (Integer) request.getAttribute("joueur"); %>
    <div class="box">
        <h1>Cr&eacute;er une partie</h1>
        <form action="Serv" method="get">
            
            <button type="submit">Cr&eacute;er</button>
            <input type="hidden" name="joueur" value="<%=joueur%>">
            <input type="hidden" name="op" value="creer_partie">
        </form>
        <h1>Rejoindre une partie</h1>
        <form action="Serv" method="get">
            <label>S&eacute;lectionner l'ID de la partie :</label><br/>
            <input type="hidden" name="joueur" value="<%=joueur%>">
            <input type="text" name="id_partie" required><br/>
            <button type="submit">Rejoindre</button>
            <input type="hidden" name="op" value="rejoindre_partie">
        </form>
    </div>
</body>
</html>