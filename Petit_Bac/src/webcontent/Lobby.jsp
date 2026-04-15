<html>
<head>
    <title>Lobby</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <form action="Serv" method="get">

        <%Joueur joueur = (Joueur) request.getAttribute("joueur");%>
        <input type="hidden" name="joueur" value="<%=joueur.getId()%>">

        <%Partie partie = (Partie) request.getAttribute("partie");%>
        <input type="hidden" name="id_partie" value="<%=partie.getId()%>">
        <h1 class="titre-encadre">ID de la partie : <%=partie.getId()%></h1>
    
        <h2 class="titre-encadre">Paramètres de la partie</h2>
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

        <h3>Joueurs connectés :</h3>
        <ul id="liste_joueurs">
            <li>En attente de joueurs...</li>
        </ul>
    
    <button type="submit">Lancer la partie</button>
    <input type="hidden" name="op" value="demarrer_partie">
    </form>

    <script>
        const socket = new WebSocket('ws://serveur-adresse/Serv');

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

            if (data.status === "START") {
                window.location.href = "Serv?op=afficher_jeu"; 
            }
        };
        
        const inputTemps = document.getElementById('temps');
        const spanTemps = document.getElementById('valeur_temps');

        function updateTemps() {
            spanTemps.innerText = inputTemps.value;
        }

        const inputTours = document.getElementById('nb_tours');
        const spanTours = document.getElementById('valeur_tours');
        const labelTours = document.querySelector('label[for="nb_tours"]');

        function updateTours() {
            const val = inputTours.value;
            spanTours.innerText = val;
            
            if (parseInt(val) > 1) {
                labelTours.innerHTML = `Nombre de tours: <span id="valeur_tours">${val}</span> tours`;
            } else {
                labelTours.innerHTML = `Nombre de tours: <span id="valeur_tours">${val}</span> tour`;
            }
        }
        inputTemps.addEventListener('input', updateTemps);
        inputTours.addEventListener('input', updateTours);

        updateTemps();
        updateTours();

        document.querySelector('form').addEventListener('submit', function(e) {
            e.preventDefault(); 
            
            const temps = document.getElementById('temps').value;
            const tours = document.getElementById('nb_tours').value;

            socket.send(JSON.stringify({ 
                action: "LANCER_PARTIE",
                temps: temps,
                tours: tours
            }));
            console.log("Demande de lancement envoyée au serveur");
        });
    </script>
</body>
</html>