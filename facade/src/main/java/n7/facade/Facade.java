
package n7.facade;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//import pack.Adresse;


@RestController
public class Facade {

    // String db_url = "jdbc:hsqldb:hsql://localhost/xdb";
    // String db_user = "sa";
    // Connection con;

    // HashMap<Integer, String> joueurs = new HashMap<Integer, String>();

    // @Autowired
    // public Facade()  {
    //     try {
    //         Class.forName("org.hsqldb.jdbc.JDBCDriver");
    //         con = DriverManager.getConnection(db_url, db_user, null);
    //     } catch (Exception e) {
    //         e.printStackTrace();;
    //     }
        
        
    // }

    @GetMapping("/genererLettre")
    private String genererLettre() {
        // generer aléatoirement une lettre qu'on va envoyer au websocker ou
        // stocker comme variable
    }

    @GetMapping("/démarrer_partie")
    public void demarrer_partie(int nb_tours, int temps) {
        // String sql = "INSERT INTO Personne (nom, prenom) VALUES ('" + nom + "', '" + prenom + "')";
        // try{
        //     Statement stmt = con.createStatement();
        //     stmt.executeUpdate(sql);
        //     stmt.close();
        // } catch(SQLException e){
        //     e.printStackTrace();
        // }

        // int id = personnes.size() + 1;
        // Personne p = new Personne(prenom, nom, id);
        // personnes.put(id, p);
    }

    @GetMapping("/ajout_surnom")
    public void ajout_surnom(String surnom) {
        // int id = joueurs.size() + 1;
        // joueurs.put(id, surnom);
    }

    @GetMapping("/Enregistrer_reponse")
    public void enregistrer_reponse(
        @RequestParam String surnom,
        @RequestParam String Pays, 
        @RequestParam String Ville, 
        @RequestParam String Prenom, 
        @RequestParam String Couleur, 
        @RequestParam String Fruit, 
        @RequestParam String Animal, 
        @RequestParam String Metier) {


        // String sql = "SELECT * FROM Personne";
        // try{
        //     Statement stmt = con.createStatement();
        //     ResultSet rs = stmt.executeQuery(sql);
        //     HashMap<Integer,Personne> personnes = new HashMap<Integer,Personne>();
        //     while(rs.next()){
        //         personnes.put(rs.getInt("id"), new Personne(rs.getString("prenom"), rs.getString("nom"), rs.getInt("id")));
        //     }
        //     stmt.close();
        //     Collection<Personne> liste = personnes.values();
        //     return liste;
        // } catch(SQLException e){
        //     e.printStackTrace();
        // }
        // Collection<Personne> list = new ArrayList<Personne>();
        // return personnes.values();
    }

    @GetMapping("/calculerPoints")
    private int calculerPoints(String reponse) {
        // 0 si pas dans bdd
        // 5 si dedans et qu'un autre joueur a le meme mot
        // 10 si mot unique et dans bdd
    }

    @GetMapping("/redemarrer_partie")
    public void redemarrer_partie(int nb_tours, int temps) {
        // Implementation for restarting the game
    }
}
