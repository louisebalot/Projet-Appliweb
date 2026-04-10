package n7.facade;

public class Joueur {

    /** Id unique du joueur */
    private int id;

    /** Nom du joueur */
    private String nom;

    /** Partie dans laquelle le joueur est */
    private Partie partie;

    /** Score du joueur */
    private int score;

    /** Ne pas utiliser */
    public Joueur() {

    }

    /**
     * Créer un joueur.
     * 
     * @param nom Nom du joueur.
     */
    public Joueur(String nom, Partie partie) {
        this.nom = nom;
        this.partie = partie;

        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Joueur))
            return false;
        Joueur other = (Joueur) o;
        
        return this.id == other.id;
    }
}