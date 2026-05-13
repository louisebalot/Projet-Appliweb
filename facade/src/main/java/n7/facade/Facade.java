
package n7.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

//import pack.Adresse;
@RestController
@CrossOrigin(origins = "*")
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
    public int creer_joueur(@RequestParam String surnom) {
        int id = joueurs.size() + 1;
        Joueur joueur = new Joueur(surnom, id);
        joueurs.add(joueur);
        return id; // Return the number of players after adding the new player
    }

    @PostMapping("/creer_partie")
    public int creer_partie(@RequestParam int id_admin) {
        int id_partie = parties.size() + 1;
        Joueur admin = joueurs.get(id_admin - 1);
        Partie partie = new Partie(admin, id_partie);
        parties.add(partie);
        return id_partie;
    }

    @PostMapping("/getListePseudos")
    public List<String> getListePseudos(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        List<Joueur> joueurs = partie.getJoueurs();
        List<String> pseudos = new ArrayList<>();

        for (int i = 0; i < joueurs.size(); i++) {
            Joueur j = joueurs.get(i);
            pseudos.add(j.getSurnom());
        }
        return pseudos;
    }

    @PostMapping("/setParametres")
    public int setParametres(@RequestParam int id_partie, @RequestParam int temps, @RequestParam int nb_tours) {
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
        return parties.get(id_partie - 1).getNumeroRoundActuel();
    }

    @PostMapping("/getTempsRound")
    public int getTempsRound(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        return partie.getRoundTime();
    }

    @PostMapping("/getNombreRounds")
    public int getNombreRounds(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        return partie.getNombreRounds();
    }

    @PostMapping("/getLettreRound")
    public String getLettreRound(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        Round round = partie.getRoundActuel();
        return round.getLettre();
    }

    @PostMapping("/enregistrer_reponse")
    public void enregistrerReponse(@RequestParam String pays, @RequestParam String ville,
            @RequestParam String prenom, @RequestParam String couleur, @RequestParam String vegetal,
            @RequestParam String animal, @RequestParam String metier,
            @RequestParam int id_partie, @RequestParam int id_joueur, @RequestParam int id_round) {

        Partie partie_actuelle = parties.get(id_partie - 1);

        Formulaire formulaireReponse = partie_actuelle.getRound(id_round).getFormulaire();
        formulaireReponse.ajouterJoueur(id_joueur);

        Joueur joueur = joueurs.get(id_joueur - 1);

        formulaireReponse.setReponseJoueur(joueur, Categorie.PAYS, pays);
        formulaireReponse.setReponseJoueur(joueur, Categorie.VILLE, ville);
        formulaireReponse.setReponseJoueur(joueur, Categorie.PRENOM, prenom);
        formulaireReponse.setReponseJoueur(joueur, Categorie.COULEUR, couleur);
        formulaireReponse.setReponseJoueur(joueur, Categorie.VEGETAL, vegetal);
        formulaireReponse.setReponseJoueur(joueur, Categorie.ANIMAL, animal);
        formulaireReponse.setReponseJoueur(joueur, Categorie.METIER, metier);
    }

    @PostMapping("/next_round")
    public boolean nextRound(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);

        if (partie.getNumeroRoundActuel() < partie.getNombreRounds() - 1) {
            partie.prochainRound();
            return true;
        }

        return false;
    }

    @PostMapping("/mise_a_jour_score")
    void miseAJourScore(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);

        partie.miseAJourScore(partie.getRoundActuel().getFormulaire());
    }

    @PostMapping("/get_vainqueur")
    int getVainqueur(@RequestParam int id_partie) {
        List<Joueur> joueurs = parties.get(id_partie - 1).getJoueurs();

        int min_score = -1;
        int min_id = -1;

        for (Joueur j : joueurs) {
            if (j.getScore() < min_score) {
                min_score = j.getScore();
                min_id = j.getId();
            }
        }

        return min_id;
    }
}
