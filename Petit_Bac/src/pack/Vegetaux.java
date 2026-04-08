package pack;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Vegetaux {
    @Id
    private String Id;

    private String nom;
    
    public Vegetaux() {
    }

    public Vegetaux(String id, String nom) {
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