package pack;

import org.jboss.marshalling.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class FormulaireTest {
    private Formulaire formVide;
    private Formulaire form;

    private Joueur j1, j2, jNullePart;

    @BeforeEach
    void SetUp() {
        j1 = new Joueur(); j1.setId(1);
        j2 = new Joueur(); j2.setId(2);
        jNullePart = new Joueur(); jNullePart.setId(0);

        formVide = new Formulaire();
        form = new Formulaire();
        form.ajouterJoueur(j1);
        form.ajouterJoueur(j2);

    }

    @Test
    void getSquelette() {
        Formulaire squelette = form.getSquelette();

        // Vérifier que toutes les réponses soient null
        for (String rep : squelette.getReponsesJoueur(j1).stream().map(Pair::getB).toList())
            assertNull(rep);

        // Vérifier que toutes les réponses soient null
        for (String rep : squelette.getReponsesJoueur(j2).stream().map(Pair::getB).toList())
            assertNull(rep);
    }

    @Test
    void getSqueletteStatique() {
        Collection<Joueur> collJoueur = new Vector<>();
        collJoueur.add(j1); collJoueur.add(j2);
        Formulaire squelette = Formulaire.getSquelette(collJoueur);

        // Vérifier que toutes les réponses soient null
        for (String rep : squelette.getReponsesJoueur(j1).stream().map(Pair::getB).toList())
            assertNull(rep);

        // Vérifier que toutes les réponses soient null
        for (String rep : squelette.getReponsesJoueur(j2).stream().map(Pair::getB).toList())
            assertNull(rep);
    }

    @Test
    void getSqueletteVide() {
        Formulaire squelette = formVide.getSquelette();

        assertThrows(JoueurNonTrouveException.class, () -> {squelette.getReponsesJoueur(j1).isEmpty();});
        assertTrue(squelette.getReponsesAUneCategorie(Categorie.PAYS).isEmpty());
    }

    @Test
    void getSqueletteStatiqueVide() {
        Collection<Joueur> collJoueur = new Vector<>();
        Formulaire squelette = Formulaire.getSquelette(collJoueur);


        assertThrows(JoueurNonTrouveException.class, () -> {squelette.getReponsesJoueur(j1).isEmpty();});
        assertTrue(squelette.getReponsesAUneCategorie(Categorie.PAYS).isEmpty());
    }

    @Test
    void ajouterJoueur() {
        assertThrows(JoueurNonTrouveException.class, () -> {form.getReponsesJoueur(jNullePart).isEmpty();});
        form.ajouterJoueur(jNullePart);
        assertFalse(form.getReponsesJoueur(jNullePart).isEmpty());
    }

    @Test
    void ajouterJoueurs() {
        Collection<Joueur> joueurs = new Vector<>();
        joueurs.add(j1);
        joueurs.add(j2);

        assertThrows(JoueurNonTrouveException.class, () -> {formVide.getReponsesJoueur(j1).isEmpty();});
        assertThrows(JoueurNonTrouveException.class, () -> {formVide.getReponsesJoueur(j2).isEmpty();});

        formVide.ajouterJoueurs(joueurs);

        assertFalse(formVide.getReponsesJoueur(j1).isEmpty());
        assertFalse(formVide.getReponsesJoueur(j2).isEmpty());
    }

    @Test
    void getReponsesAUneCategorie() {
        assertTrue(formVide.getReponsesAUneCategorie(Categorie.ANIMAL).isEmpty());

        form.setReponseJoueur(j1, Categorie.ANIMAL, "Chien");
        form.setReponseJoueur(j1, Categorie.VILLE, "Seix");

        form.setReponseJoueur(j2, Categorie.ANIMAL, "Chat");

        Collection<String> reponsesAnimal = form.getReponsesAUneCategorie(Categorie.ANIMAL);
        Collection<String> reponsesVille = form.getReponsesAUneCategorie(Categorie.VILLE);

        // Autant de réponses que de joueurs
        assertEquals(reponsesAnimal.size(), 2);
        assertEquals(reponsesVille.size(), 2);

        for (String s : form.getReponsesAUneCategorie(Categorie.PAYS)) {
            assertNull(s);
        }

        assertTrue(reponsesAnimal.contains("Chien"));
        assertTrue(reponsesAnimal.contains("Chat"));
        assertTrue(reponsesVille.contains("Seix"));

    }

    @Test
    void setEtGetReponseJoueur() {
        assertNull(form.getReponseJoueur(j1, Categorie.PAYS));

        form.setReponseJoueur(j1, Categorie.PAYS, "Espagne");
        assertEquals(form.getReponseJoueur(j1, Categorie.PAYS), "Espagne");

        form.setReponseJoueur(j1, Categorie.PAYS, "Pérou");
        assertEquals(form.getReponseJoueur(j1, Categorie.PAYS), "Pérou");
    }

    @Test
    void getReponsesJoueurInexistant() {
        assertThrows(JoueurNonTrouveException.class, () -> {formVide.getReponseJoueur(jNullePart, Categorie.ANIMAL);});
        assertThrows(JoueurNonTrouveException.class, () -> {formVide.getReponsesJoueur(j1);});
    }
}