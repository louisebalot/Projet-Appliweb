package n7.facade;

import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * Une entrée dans un formulaire (correspond à toutes les réponses d'un joueur)
 */
public class EntreeFormulaire {
    /**
     * Id du joueur.
     */
    private int idJoueur;
    /**
     * Lettre choisie par le joueur.
     */
    private List<String> reponses;

    /**
     * Ne pas utiliser (svp).
     */
    public EntreeFormulaire() {
        this.reponses = new Vector<>(Categorie.values().length);
        for (int i = 0; i < Categorie.values().length; i++) {
            reponses.add(null);
        }
    }

    /**
     * Créer une entrée de formulaire à partir d'un joueur, d'une catégorie et de sa réponse.
     *
     * @param joueur    Joueur lié à l'entrée
     * @param categorie Catégorie de la réponse
     * @param reponse   Réponse à la catégorie
     */
    public EntreeFormulaire(Joueur joueur, Categorie categorie, String reponse) {
        this(joueur.getId(), categorie, reponse);
    }

    /**
     * Créer une entrée de formulaire à partir d'un joueur.
     *
     * @param joueur joueur
     */
    public EntreeFormulaire(Joueur joueur) {
        this(joueur.getId());
    }

    /**
     * Créer une entrée de formulaire à partir de l'id d'un joueur.
     *
     * @param idJoueur id du joueur
     */
    public EntreeFormulaire(int idJoueur) {
        this();
        this.idJoueur = idJoueur;
    }

    /**
     * Créer une entrée de formulaire à partir d'un joueur, d'une catégorie et de sa réponse.
     *
     * @param idJoueur  id du joueur.
     * @param categorie catégorie à laquelle on veut répondre.
     * @param reponse   réponse à la catégorie.
     */
    public EntreeFormulaire(int idJoueur, Categorie categorie, String reponse) {
        this(idJoueur);
        this.reponses.set(categorie.ordinal(), reponse);
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    /**
     * Obtenir la réponse à une catégorie.
     *
     * @param categorie catégorie qu'on veut vérifier.
     * @return
     */
    public String getReponse(Categorie categorie) {
        return reponses.get(categorie.ordinal());
    }

    public void setReponse(Categorie categorie, String reponse) {
        this.reponses.set(categorie.ordinal(), reponse);
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

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Formulaire du joueur " + idJoueur + ":\n");

        for (Categorie cat : Categorie.values()) {
            str.append("   ");
            str.append(cat.name());
            str.append(" : ");
            String response = reponses.get(cat.ordinal());
            if (response != null) str.append(reponses.get(cat.ordinal()));
            str.append("\n");
        }

        return str.toString();
    }
}
