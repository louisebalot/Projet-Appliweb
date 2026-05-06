<%@ page import="pack.Joueur" %>
<%@ page import="pack.Partie" %>
<%@ page import="pack.Round" %>
<%
    Partie partie = (Partie) request.getAttribute("partie");
    Joueur joueur = (Joueur) request.getAttribute("joueur");
    Round round = (Round) request.getAttribute("round");
%>
<html>
<head>
    <title>Formulaire de réponse</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <div id="countdown-overlay">
        <h1>Préparez-vous !</h1>
        <div id="countdown-number">3</div>
    </div>

    <div class="box-form">
        <h1>C'est tipar !</h1>
        <h2 class="titre-encadre">Lettre : <span id="lettre"><%= round.getLettre() %></span></h2>

        <form action="Serv" method="get">
            <input type="hidden" name="id_partie" value="<%=partie.getId()%>">
            <input type="hidden" name="joueur" value="<%=joueur.getId()%>">
            <div class="cadre-parametres">
                <div class="groupe-input">
                    <label class="label-categorie" for="Pays">Pays</label>
                    <input type="text" id="Pays" name="Pays" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Ville">Ville</label>
                    <input type="text" id="Ville" name="Ville" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Prenom">Prénom</label>
                    <input type="text" id="Prenom" name="Prenom" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Couleur">Couleur</label>
                    <input type="text" id="Couleur" name="Couleur" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Vegetal">Végétal</label>
                    <input type="text" id="Vegetal" name="Vegetal" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Animal">Animal</label>
                    <input type="text" id="Animal" name="Animal" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Metier">Métier</label>
                    <input type="text" id="Metier" name="Metier" autocomplete="off">
                </div>                
                
                <div class="groupe-input">
                    <label class="label-categorie" for="Sport">Sport</label>
                    <input type="text" id="Sport" name="Sport" autocomplete="off">
                </div>
            </div>

            <button type="submit" id="btn-fini">FINI !!!!</button>
            <input type="hidden" name="op" value="Enregistrer_reponse">
        </form>
    </div>

    <script>
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/Serv');

        const overlay = document.getElementById('countdown-overlay');
        const displayDecompte = document.getElementById('countdown-number');
        let decompteInitial = 3;

        const intervalInitial = setInterval(() => {
            decompteInitial--;

            if (decompteInitial > 0) {
                displayDecompte.innerText = decompteInitial;
            } else if (decompteInitial === 0) {
            } else {
                clearInterval(intervalInitial);
                
                overlay.classList.add('hidden'); 
                
                setTimeout(() => {
                    overlay.style.display = 'none';
                    //demarrerLeChronoDeLaPartie();
                }, 500);
            }
        }, 1000);

        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "FINI") {
                stopperLaPartie();
                envoyerReponses();
            }
        };

        function stopperLaPartie() {
            const inputs = document.querySelectorAll('input');
            for (let input of inputs) {
                input.readOnly = true; 
                input.style.backgroundColor = "#e0e0e0";
            }
            alert("STOP ! La partie est terminée !");
            document.querySelector('button').style.display = 'none';
        }

        function envoyerReponses() {
            const formulaire = document.querySelector('form');
            formulaire.submit(); 
        }

        document.querySelector('form').addEventListener('submit', function(e) {
            e.preventDefault(); 
            socket.send(JSON.stringify({ action: "STOP" }));
            console.log("Signal STOP envoyé !");
        });
    </script>
</body>
</html>
