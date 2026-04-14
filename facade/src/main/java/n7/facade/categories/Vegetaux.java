package n7.facade;

public class Vegetaux {
    private String nom;
    
    public Vegetaux() {
    }

    public Vegetaux(String id, String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}