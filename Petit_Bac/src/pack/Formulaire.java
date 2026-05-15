package pack;

import org.jboss.marshalling.Pair;

import java.util.Collection;
import java.util.List;
import java.util.Vector;


/**
 * Classe d'un formulaire de réponse
 */
public class Formulaire {
    private List<EntreeFormulaire> form;

    /**
     * Créer un formulaire.
     */
    public Formulaire() {
        form = new Vector<>();
    }

    /**
     * Renvoyer un squelette de formulaire à partir d'une collection de Joueur
     *
     * @param joueurs collection de joueur
     * @return formulaire avec toutes les réponses mises à {@code null}
     */
    public static Formulaire getSquelette(Collection<Joueur> joueurs) {
        Formulaire nouveauForm = new Formulaire();

        nouveauForm.ajouterJoueurs(joueurs);

        return nouveauForm;
    }

    /**
     * Ajouter un joueur au formulaire.
     *
     * @param joueur joueur à ajouter
     */
    public void ajouterJoueur(Joueur joueur) {
        form.add(new EntreeFormulaire(joueur));
    }

    /**
     * Ajouter une collection de joueur au formulaire.
     *
     * @param joueurs collection de joueurs à ajouter
     */
    public void ajouterJoueurs(Collection<Joueur> joueurs) {

        for (Joueur j : joueurs) {
            ajouterJoueur(j);
        }
    }

    /**
     * Obtenir le squelette du formulaire.
     *
     * @return Un formulaire avec tous les joueurs sans la réponse remplie.
     */
    public Formulaire getSquelette() {
        Formulaire nouveauForm = new Formulaire();

        for (EntreeFormulaire entree : form) {
            nouveauForm.ajouterJoueur(entree.getIdJoueur());
        }

        return nouveauForm;
    }

    /**
     * Obtenir toutes les réponses à une catégorie.
     *
     * @param categorie catégorie cherchée.
     * @return Collection de l'ensemble des réponses à une catégorie.
     */
    public Collection<String> getReponsesAUneCategorie(Categorie categorie) {
        Collection<String> reponses = new Vector<>();

        for (EntreeFormulaire entree : form) {
            reponses.add(entree.getReponse(categorie));
        }

        return reponses;
    }

    /**
     * Obtenir la réponse d'un joueur à une catégorie donnée.
     *
     * @param joueur    joueur
     * @param categorie catégorie
     * @return réponse du joueur à la catégorie
     */
    public String getReponseJoueur(Joueur joueur, Categorie categorie) {
        return getEntreesJoueur(joueur).getReponse(categorie);
    }

    /**
     * Obtenir toutes les réponses d'un joueur comme une paire [Catégorie, Réponse (String)]
     *
     * @param joueur Joueur
     * @return La liste des couples Catégorie/Réponse donnée par le joueur
     */
    public Collection<Pair<Categorie, String>> getReponsesJoueur(Joueur joueur) {
        Collection<Pair<Categorie, String>> reponses = new Vector<>();


        EntreeFormulaire reponsesJoueur = getEntreesJoueur(joueur);

        for (Categorie cat : Categorie.values()) {
            reponses.add(new Pair<>(cat, reponsesJoueur.getReponse(cat)));
        }

        return reponses;
    }

    public void setReponseJoueur(Joueur joueur, Categorie categorie, String reponse) {
        getEntreesJoueur(joueur).setReponse(categorie, reponse);
    }

    /**
     * Ajouter un joueur à partir de son id.
     *
     * @param idJoueur id du joueur
     */
    public void ajouterJoueur(int idJoueur) {
        form.add(new EntreeFormulaire(idJoueur));
    }

    private EntreeFormulaire getEntreesJoueur(Joueur joueur) {
        EntreeFormulaire entree = null;

        int i = 0;
        while (i < form.size() && entree == null) {
            if (form.get(i).getIdJoueur() == joueur.getId()) {
                entree = form.get(i);
            }
            i++;
        }

        if (entree == null) throw new JoueurNonTrouveException("Le joueur " + joueur.getSurnom()
                + " {id=" + joueur.getId() + "} n'existe pas dans le formulaire");

        return entree;
    }
}
