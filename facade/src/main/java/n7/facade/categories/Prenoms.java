package n7.facade.categories;


public class Prenoms {
    
    private String nom;
    
    public Prenoms() {
    }

    public Prenoms(String id, String nom) {
        this.nom = nom;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}