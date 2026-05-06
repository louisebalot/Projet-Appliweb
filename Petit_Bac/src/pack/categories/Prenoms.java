package pack.categories;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Prenoms {
    @Id
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