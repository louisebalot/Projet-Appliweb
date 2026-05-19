<%
    int partie = (Integer) request.getAttribute("partie");
    int joueur = (Integer) request.getAttribute("joueur");
    int round = (Integer) request.getAttribute("round");
    int temps = (Integer) request.getAttribute("temps");
    int nb_rounds = (Integer) request.getAttribute("nb_rounds");
    String lettre = (String) request.getAttribute("lettre");
%>
<html>
<head>
    <title>Formulaire de r&eacute;ponse</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <div id="countdown-overlay">
        <h1>Pr&eacute;parez-vous !</h1>
        <div id="countdown-number"><%= 3 %></div>
    </div>

    <div class="box-form">
        <h1>C'est tipar : Manche <%= round + 1%> / <%= nb_rounds %>!</h1>

        <div id ="countdown-partie">
            <h1>Il reste :</h1>
            <div id="countdown-start"><%= temps %></div>
        </div>

        <h2 class="titre-encadre">Lettre : <span id="lettre"><%= lettre %></span></h2>

        <form action="Serv" method="get">
            <input type="hidden" name="id_partie" value="<%=partie%>">
            <input type="hidden" name="joueur" value="<%=joueur%>">
            <input type="hidden" name="round" value="<%=round%>">
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
                    <label class="label-categorie" for="Prenom">Pr&eacute;nom</label>
                    <input type="text" id="Prenom" name="Prenom" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Couleur">Couleur</label>
                    <input type="text" id="Couleur" name="Couleur" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Vegetal">V&eacute;g&eacute;tal</label>
                    <input type="text" id="Vegetal" name="Vegetal" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Animal">Animal</label>
                    <input type="text" id="Animal" name="Animal" autocomplete="off">
                </div>

                <div class="groupe-input">
                    <label class="label-categorie" for="Metier">M&eacute;tier</label>
                    <input type="text" id="Metier" name="Metier" autocomplete="off">
                </div>
                
                <div class="groupe-input">
                    <label class="label-categorie" for="Sport">Sport</label>
                    <input type="text" id="Sport" name="Sport" autocomplete="off">
                </div>
            </div>
            
            <button type="submit" id="btn-fini">FINI !!!!</button>

            <div id="custom-alert" class="modal-overlay" style="display: none;">
                <div class="modal-content">
                    <h2 class="titre-encadre">Termin&eacute; !</h2>
                    <p>Le round est fini.</p>
                </div>
            </div>
            
            <input type="hidden" name="op" value="Enregistrer_reponse">
        </form>
    </div>

    <script>
        const socket = new WebSocket('ws://' + window.location.host + '/Petit_Bac/ws');

        const overlay = document.getElementById('countdown-overlay');
        const countdownPartie = document.getElementById('countdown-partie');
        const displayDecompte = document.getElementById('countdown-number');
        const displayCountdown = document.getElementById('countdown-start');
        let decompteInitial = 3;
        let decomptePartie = parseInt('<%= temps %>');

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
                    demarrerLeChronoDeLaPartie();
                }, 500);
            }
        }, 1000);

        function demarrerLeChronoDeLaPartie() {
            const intervalPartie = setInterval(() => {
                decomptePartie--;

                if (decomptePartie >= 0) {
                    displayCountdown.innerText = decomptePartie;
                } else {
                    clearInterval(intervalPartie);

                    setTimeout(() => {
                        stopperLaPartie();
                        setTimeout(envoyerReponses, 3000);
                    }, 500);
                }
            }, 1000);
        }


        let reponsesEnvoyees = false;

        function stopperLaPartie() {
            document.querySelectorAll('input[type="text"]').forEach(input => {
                input.readOnly = true;
                input.style.backgroundColor = "#e0e0e0";
            });

            const btn = document.getElementById('btn-fini');
            if (btn) btn.style.display = 'none';

            const modale = document.getElementById('custom-alert');
            if (modale) modale.style.display = 'flex';
        }

        function envoyerReponses() {
            if (!reponsesEnvoyees) {
                reponsesEnvoyees = true;
                document.querySelector('form').submit(); 
            }
        }

        // Gestion des messages WebSocket reçus
        socket.onmessage = function(event) {
            const data = JSON.parse(event.data);
            if (data.status === "FINI" || data.status === "STOP") {
                stopperLaPartie();
                setTimeout(envoyerReponses, 3000); 
            }
        };

        document.querySelector('form').addEventListener('submit', function(e) {
            e.preventDefault(); 
            
            socket.send(JSON.stringify({ action: "STOP" }));
            console.log("Signal STOP envoyé !");
            
            stopperLaPartie();
            setTimeout(envoyerReponses, 3000);
        });

    </script>
</body>
</html>
