<%
    int partie = (Integer) request.getAttribute("partie");
    int joueur = (Integer) request.getAttribute("joueur");
    int round = (Integer) request.getAttribute("round");
    int temps = (Integer) request.getAttribute("temps");
    String lettre = (String) request.getAttribute("lettre");
%>
<html>
<head>
    <title>Formulaire de réponse</title>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
</head>
<body>
    <div id="countdown-overlay">
        <h1>Pr&eacute;parez-vous !</h1>
        <div id="countdown-number"><%= 3 %></div>
    </div>

    <div class="box-form">
        <h1>C'est tipar !</h1>
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
            
            <div id="countdown" >
                <h1>Pr&eacute;parez-vous !</h1>
                <div id="countdown-number"><%= temps %></div>
            </div>
            
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
            alert("STOP ! Le round est fini !");
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
