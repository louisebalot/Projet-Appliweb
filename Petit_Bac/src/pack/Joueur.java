package pack;

import java.util.Objects;

public class Joueur {

    /**
     * Id unique du joueur
     */
    private int id;

    /**
     * Surnom du joueur
     */
    private String surnom;

    /**
     * Partie dans laquelle le joueur est
     */
    private Partie partie;

    /**
     * Score du joueur
     */
    private int score;

    /**
     * Ne pas utiliser
     */
    public Joueur() {

    }

    /**
     * Créer un joueur.
     *
     * @param surnom Surnom du joueur.
     */
    public Joueur(String surnom) {
        this.surnom = surnom;
        this.score = 0;
    }

    public Joueur(String surnom, int id) {
        this(surnom);
        this.id = id;
    }


    public void ajouterScore(int score) {
        this.score += score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return id == joueur.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}