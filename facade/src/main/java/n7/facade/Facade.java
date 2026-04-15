
package n7.facade;

//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//import pack.Adresse;

@RestController
public class Facade {

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
    public Joueur creer_joueur(String surnom, int id) {
        return new Joueur(surnom,id);
    }

    @PostMapping("/creer_partie")
    public Partie creer_partie(Joueur joueur, int id) {
        Partie partie = new Partie(joueur, id);
        return partie;
    }

    @PostMapping("/rejoindre_partie")
    public void rejoindre_partie(Joueur joueur, Partie partie) {}

    @PostMapping("/démarrer_partie")
    public void demarrer_partie(Partie partie, int nb_tours, int temps) {
        partie.creerRounds(nb_tours, temps);
        
    }

    @PostMapping("/Enregistrer_reponse")
    public void enregistrer_reponse(String surnom, String Pays, String Ville,
            String Prenom, String Couleur, String Fruit, String Animal, String Metier) {
        
        
    }

    @PostMapping("/calculerPoints")
    private int calculerPoints(String reponse) {
        // 0 si pas dans bdd
        // 5 si dedans et qu'un autre joueur a le meme mot
        // 10 si mot unique et dans bdd
        return 0;
    }

    @PostMapping("/redemarrer_partie")
    public void redemarrer_partie(int nb_tours, int temps) {
        // Implementation for restarting the game
    }
}
