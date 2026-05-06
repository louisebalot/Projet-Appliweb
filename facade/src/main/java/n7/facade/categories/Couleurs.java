package n7.facade.categories;


public class Couleurs {
    
    private String nom;
    
    public Couleurs() {
    }

    public Couleurs(String id, String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}