<%@ page import="java.util.List" %>
<%@ page import="pack.ScoreLigne" %>
<% int joueur = (Integer) request.getAttribute("joueur");
    int partie = (Integer) request.getAttribute("partie");
    int score = (Integer) request.getAttribute("score");
    String pseudoVainqueur = (String) request.getAttribute("pseudo_vainqueur");
    List<ScoreLigne> classement = (List<ScoreLigne>) request.getAttribute("classement");
%>
<html>
<head>
    <title>Page de victoire</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>

    <div class="box">
        <h1 id="message_victoire">Vainqueur : <%= pseudoVainqueur %> !</h1>

        <p id="message_score">Votre score : <strong><%=score%></strong> points.</p>
        
        <div id="zone-classement">
            <h2 class="titre-encadre">Classement</h2>
            
            <ul id="liste-scores">
                <% if (classement != null) {
                    for (ScoreLigne ligne : classement) { %>
                    <li><strong><%= ligne.getPseudo() %></strong> : <%= ligne.getScore() %> pts</li>
                <% }
                } %>
            </ul>
        </div>

        <br>
        
        <form action="Serv" method="get">
            <input type="hidden" name="joueur" value="<%=joueur%>">
            <input type="hidden" name="op" value="redemarrer_partie">
            <button type="submit">REJOUER</button>
        </form>
    </div>
</body>
</html>