package n7.facade.categories;



public class Sports {
    
    private String nom;
    
    public Sports() {
    }

    public Sports(String id, String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}