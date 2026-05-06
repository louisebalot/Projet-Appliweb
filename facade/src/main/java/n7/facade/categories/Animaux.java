package n7.facade.categories;



public class Animaux {
    
    private String nom;
    
    public Animaux() {
    }

    public Animaux(String id, String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}