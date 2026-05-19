
package n7.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CopyOnWriteArrayList;


//import pack.Adresse;
@RestController
@CrossOrigin(origins = "*")
public class Facade {

    //java.util.List<Partie> parties = new java.util.ArrayList<>();
    //java.util.List<Joueur> joueurs = new java.util.ArrayList<>();

    List<Partie> parties = new CopyOnWriteArrayList<>();
    List<Joueur> joueurs = new CopyOnWriteArrayList<>();

    private Map<String, CountDownLatch> latchs = new ConcurrentHashMap<>();

    // Clé unique par partie+round
    private String latchKey(int id_partie, int id_round) {
        return id_partie + "_" + id_round;
}

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
        admin.setIsAdmin(true);
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
        Partie partie = parties.get(id_partie - 1);
        int nbJoueurs = partie.getJoueurs().size();
        int numRound = partie.getNumeroRoundActuel();

        // Créer un latch pour ce round
        String key = latchKey(id_partie, numRound);
        latchs.put(key, new CountDownLatch(nbJoueurs));

        return numRound;
    }

    @PostMapping("/getTempsRound")
    public int getTempsRound(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        return partie.getRoundTime();
    }

    @PostMapping("/getNumeroRoundActuel")
    public int getNumeroRoundActuel(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        return partie.getNumeroRoundActuel();
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
            @RequestParam String animal, @RequestParam String metier, @RequestParam String sport,
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
        formulaireReponse.setReponseJoueur(joueur, Categorie.SPORT, sport);

        String key = latchKey(id_partie, id_round);
        CountDownLatch latch = latchs.get(key);
        if (latch != null) {
            latch.countDown();
        }
    }

    @PostMapping("/next_round")
    public int nextRound(@RequestParam int id_partie, @RequestParam int id_joueur) {
        Partie partie = parties.get(id_partie - 1);
        Joueur joueur = joueurs.get(id_joueur - 1);

        if (!joueur.getIsAdmin()) {
            return 1; // Only the admin can start the next round
        } else {

            if (partie.getNumeroRoundActuel() < partie.getNombreRounds() - 1) {
                partie.prochainRound();
                return 0;
            }

            return 2;
        }
    }

    @PostMapping("/mise_a_jour_score")
    void miseAJourScore(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);

        partie.miseAJourScore(partie.getRoundActuel().getFormulaire());
    }

    @PostMapping("/isPartieFinie")
    boolean isPartieFinie(@RequestParam int id_partie) {
        Partie partie = parties.get(id_partie - 1);
        return (partie.getNumeroRoundActuel() >= partie.getNombreRounds());
    }
    

    @PostMapping("/isJoueurAdmin")
    boolean isJoueurAdmin(@RequestParam int id_joueur, @RequestParam int id_partie){
        Partie partie = parties.get(id_partie - 1);
        Joueur admin = partie.getAdmin();
        return (id_joueur == admin.getId());
    }


    @PostMapping("/get_vainqueur")
    int getVainqueur(@RequestParam int id_partie) {
        List<Joueur> joueurs = parties.get(id_partie - 1).getJoueurs();

        int max_score = -1;
        int max_id = -1;

        for (Joueur j : joueurs) {
            if (j.getScore() > max_score) {
                max_score = j.getScore();
                max_id = j.getId();
            }
        }
        return max_id;
    }

    @PostMapping("/reset_score")
    public void resetScore(@RequestParam int id_joueur) {
        Joueur joueur = joueurs.get(id_joueur - 1);
        joueur.setScore(0);
    }

    @PostMapping("/get_score")
    public int getScore(@RequestParam int id_partie, @RequestParam int id_joueur) {
        Partie partie = parties.get(id_partie - 1);
        List<Joueur> joueurs2 = partie.getJoueurs();
        for (Joueur j : joueurs2) {
            if (j.getId() == id_joueur) {
                return j.getScore();
            }
        }
        return 200;
    }

    @PostMapping("/attendre_reponses")
    public void attendreReponses(@RequestParam int id_partie, @RequestParam int id_round)
            throws InterruptedException {
        String key = latchKey(id_partie, id_round);
        CountDownLatch latch = latchs.get(key);
        if (latch != null) {
            latch.await();
            latchs.remove(key);
        }
    }
}