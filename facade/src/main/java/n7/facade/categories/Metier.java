package n7.facade.categories;



public class Metier {

    private String nom;
    
    public Metier() {
    }

    public Metier(String id, String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}