
package n7.facade;

//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import pack.Adresse;

@RestController
public class Facade {

    java.util.List<Partie> parties = new java.util.ArrayList<>();
    java.util.List<Joueur> joueurs = new java.util.ArrayList<>();

    // String db_url = "jdbc:hsqldb:hsql://localhost/xdb";
    // String db_user = "sa";
    // Connection con;

    // HashMap<Integer, String> joueurs = new HashMap<Integer, String>();

    // @Autowired
    // public Facade() {
    // try {
    // Class.forName("org.hsqldb.jdbc.JDBCDriver");
    // con = DriverManager.getConnection(db_url, db_user, null);
    // } catch (Exception e) {
    // e.printStackTrace();;
    // }

    // }

    @PostMapping("/creer_joueur")
    public int creer_joueur(@RequestParam String surnom, @RequestParam int id) {
        Joueur joueur = new Joueur(surnom, id);
        joueurs.add(joueur);
        return joueurs.size(); // Return the number of players after adding the new player
    }

    @PostMapping("/creer_partie")
    public int creer_partie(@RequestParam int id_admin) {
        int id_partie = parties.size() + 1;
        Joueur admin = joueurs.get(id_admin - 1);
        Partie partie = new Partie(admin, id_partie);
        parties.add(partie);
        return id_partie;
    }

    @PostMapping("/setParametres")
    public int setParametres(@RequestParam int id_partie, @RequestParam int temps, @RequestParam int nb_tours){
        Partie partie = parties.get(id_partie - 1);
        partie.setRoundTime(temps);
        partie.setNombreRounds(nb_tours);
        partie.creerRounds();
        return id_partie;
    }

    @PostMapping("/rejoindre_partie")
    public void rejoindre_partie(@RequestParam int id_joueur, @RequestParam int id_partie) {
        Joueur invite = joueurs.get(id_joueur - 1);
        Partie partie = parties.get(id_partie - 1);
        partie.ajouterJoueur(invite);
    }

    @PostMapping("/demarrer_round")
    public int demarrer_round(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        int id_round = partie.getNumeroRoundActuel();
        return id_round;
    }

    @PostMapping("/getLettreRound")
    public String getLettreRound(@RequestParam int id_partie, @RequestParam int id_round) {
        Partie partie = parties.get(id_partie - 1);
        Round round = partie.getRound(id_round);
        return round.getLettre();
    }

    @PostMapping("/Enregistrer_reponse")
    public void enregistrer_reponse(@RequestParam String Pays, @RequestParam String Ville,
            @RequestParam String Prenom, @RequestParam String Couleur, @RequestParam String Fruit, @RequestParam String Animal, @RequestParam String Metier,
            @RequestParam int id_partie, @RequestParam int id_joueur, @RequestParam int id_round) {
        
        Partie partie_actuelle = parties.get(id_partie - 1);
        
        Formulaire formulaireReponse = partie_actuelle.getRound(id_round).getFormulaire();
        formulaireReponse.ajouterJoueur(id_joueur);

        Joueur joueur = joueurs.get(id_joueur - 1);

        formulaireReponse.setReponseJoueur(joueur, Categorie.PAYS, Pays);
        formulaireReponse.setReponseJoueur(joueur, Categorie.VILLE, Ville);
        formulaireReponse.setReponseJoueur(joueur, Categorie.PRENOM, Prenom);
        formulaireReponse.setReponseJoueur(joueur, Categorie.COULEUR, Couleur);
        formulaireReponse.setReponseJoueur(joueur, Categorie.VEGETAL, Fruit);
        formulaireReponse.setReponseJoueur(joueur, Categorie.ANIMAL, Animal);
        formulaireReponse.setReponseJoueur(joueur, Categorie.METIER, Metier);
    }

    @PostMapping("/next_round")
    public boolean nextRound(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);

        if (partie.getNumeroRoundActuel() < partie.getNombreRounds()) {
            partie.prochainRound();
            return true;
        }
        
        return false;
    }

    @PostMapping("/calculerPoints")
    private void calculerPoints(Formulaire formulaire, @RequestParam int id_partie, @RequestParam int id_round) {
        Partie partie = parties.get(id_partie - 1);
        Round round = partie.getRound(id_round);
        Formulaire formulaireRound = round.getFormulaire();
        partie.miseAJourScore(formulaireRound);
    }

    @PostMapping("/redemarrer_partie")
    public void redemarrer_partie(@RequestParam int nb_tours, @RequestParam int temps) {
        // Implementation for restarting the game
    }
}
