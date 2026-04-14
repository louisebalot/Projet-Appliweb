package pack;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe d'un formulaire de réponse
 */
public class Formulaire {
    private Set<EntreeFormulaire> form;

    /**
     * Créer un formulaire
     */
    public Formulaire() {
        form = new HashSet<>();
    }

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
     * Ajouter un joueur à partir de son id.
     *
     * @param idJoueur
     */
    private void ajouterJoueur(int idJoueur) {
        form.add(new EntreeFormulaire(idJoueur));
    }


}
