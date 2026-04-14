package pack;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Vegetaux {
    @Id
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