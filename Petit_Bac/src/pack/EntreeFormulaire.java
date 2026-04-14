package pack;

import java.util.Objects;

public class EntreeFormulaire {
    /**
     * Id du joueur.
     */
    private int idJoueur;
    /**
     * Lettre choisie par le joueur.
     */
    private char lettre;

    public EntreeFormulaire() {

    }

    public EntreeFormulaire(Joueur joueur, char lettre) {
        this(joueur.getId(), lettre);
    }

    public EntreeFormulaire(Joueur joueur) {
        this(joueur.getId());
    }

    public EntreeFormulaire(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public EntreeFormulaire(int idJoueur, char lettre) {
        this(idJoueur);
        this.lettre = lettre;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public char getLettre() {
        return lettre;
    }

    public void setLettre(char lettre) {
        this.lettre = lettre;
    }

    // Deux entrées sont égales si c'est le même joueur qui les a émises
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntreeFormulaire that = (EntreeFormulaire) o;
        return idJoueur == that.idJoueur;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idJoueur);
    }
}
