package pack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntreeFormulaireTest {
    private EntreeFormulaire entree1, entree2;

    @BeforeEach
    void beforeEach() {
        entree1 = new EntreeFormulaire(1, Categorie.ANIMAL, "Lapin");
        entree2 = new EntreeFormulaire(2, Categorie.METIER, "Boulanger");
    }

    @Test
    void testEquals() {
        EntreeFormulaire entreeDouble = new EntreeFormulaire(1, Categorie.PAYS, "Finlande");

        assertEquals(entreeDouble, entree1);
    }

    @Test
    void getIdJoueur() {
        assertEquals(entree1.getIdJoueur(), 1);
        assertEquals(entree2.getIdJoueur(), 2);
    }

    @Test
    void setIdJoueur() {
        entree1.setIdJoueur(23);
        assertEquals(entree1.getIdJoueur(), 23);
    }

    @Test
    void getReponse() {
        assertEquals(entree1.getReponse(Categorie.ANIMAL), "Lapin");
        assertNull(entree1.getReponse(Categorie.PAYS));
    }

    @Test
    void setReponse() {
        entree1.setReponse(Categorie.ANIMAL, "Serpent");
        assertEquals(entree1.getReponse(Categorie.ANIMAL),  "Serpent");

        assertNull(entree1.getReponse(Categorie.VILLE));
        entree1.setReponse(Categorie.VILLE, "Foix");
        assertEquals(entree1.getReponse(Categorie.VILLE),  "Foix");
    }
}