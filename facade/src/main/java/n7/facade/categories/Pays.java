package n7.facade;

public class Pays {
    private String Id;
    private String nom;
    
    public Pays() {
    }

    public Pays(String id, String nom) {
        this.Id = id;
        this.nom = nom;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}

