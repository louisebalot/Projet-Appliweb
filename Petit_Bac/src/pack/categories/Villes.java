package pack;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Villes {
    @Id
    private String nom;
    
    public Villes() {
    }

    public Villes(String id, String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
}