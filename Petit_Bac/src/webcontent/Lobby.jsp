<%@ page import="pack.Joueur" %>
<%@ page import="pack.Partie" %>
<html>
<head>
    <title>Lobby</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <form action="Serv" method="get">

        <%int joueur = (Integer) request.getAttribute("joueur");%>
        <input type="hidden" name="id_joueur" value="<%=joueur%>">

        <%int partie = (Integer) request.getAttribute("partie");%>
        <input type="hidden" name="id_partie" value="<%=partie%>">
        <h1 class="titre-encadre">ID de la partie : <%=partie%></h1>
    
        <h2 class="titre-encadre">Param&egrave;tres de la partie</h2>
        <div class="cadre-parametres">

            <div class="groupe-input">
            <input type="range" id="temps" name="temps" min="1" max="5" list="temps_list"> 
                <datalist id="temps_list">
                <option value="1"></option>
                <option value="2"></option>
                <option value="3"></option>
                <option value="4"></option>
                <option value="5"></option>
                </datalist>
            <label for="temps">Temps : <span id="valeur_temps">2</span> min</label>
            </div>

            <div class="groupe-input">
            <input type="range" id="nb_tours" name="nb_tours" min="1" max="6" list="nb_tours_list" />
                <datalist id="nb_tours_list">
                <option value="1"></option>
                <option value="2"></option>
                <option value="3"></option>
                <option value="4"></option>
                <option value="5"></option>
                <option value="6"></option>
                </datalist>
            <label for="nb_tours">Nombre de tours: <span id="valeur_tours">2</span> tours</label>
            </div>
        </div>

        <h3>Joueurs connect&eacute;s :</h3>
        <ul id="liste_joueurs">
            <li>En attente de joueurs...</li>
        </ul>
        
        <button type="submit">Valider</button>
        <input type="hidden" name="op" value="valider_params">
    </form>

    <script>
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/Serv');

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);

            if (data.status === "UPDATE_PLAYERS") {
                const ul = document.getElementById('liste_joueurs');
                ul.innerHTML = "";
                
                data.joueurs.forEach(surnom => {
                    const li = document.createElement('li');
                    li.textContent = surnom;
                    ul.appendChild(li);
                });
            }
        };
        
        const inputTemps = document.getElementById('temps');
        const spanTemps = document.getElementById('valeur_temps');
        inputTemps.addEventListener('input', () => spanTemps.innerText = inputTemps.value);

        const inputTours = document.getElementById('nb_tours');
        const spanTours = document.getElementById('valeur_tours');
        inputTours.addEventListener('input', () => spanTours.innerText = inputTours.value);
    </script>
</body>
</html>