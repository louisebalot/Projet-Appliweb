package pack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PartieTest {
    Joueur j1, j2, j3;
    Partie partie;
    Formulaire form;

    @BeforeEach
    void setUp() {
        j1 = new Joueur("Louise"); j1.setId(1);
        j2 = new Joueur("Adrien"); j2.setId(2);
        j3 = new Joueur("Camille"); j3.setId(3);

        partie = new Partie(j1);
        partie.ajouterJoueur(j2);
        partie.ajouterJoueur(j3);
        partie.creerRounds(3, 10);

        partie.getRoundActuel().setLettre("F");

        form = Formulaire.getSquelette(partie.getJoueurs());
        form.setReponseJoueur(j1, Categorie.ANIMAL, "Fennec");
        form.setReponseJoueur(j1, Categorie.VILLE, "Tours");
        form.setReponseJoueur(j1, Categorie.VEGETAL, "Fraise");

        form.setReponseJoueur(j2, Categorie.ANIMAL, "Fennec");
        form.setReponseJoueur(j2, Categorie.VILLE, "Saint-Jean-lès-Buzy");
        form.setReponseJoueur(j2, Categorie.PAYS, "Finlande");

        form.setReponseJoueur(j3, Categorie.ANIMAL, "Fennec");
        form.setReponseJoueur(j3, Categorie.VILLE, "Foix");
        form.setReponseJoueur(j3, Categorie.METIER, "Coiffeur");
    }

    @Test
    void ajouterJoueur() {
    }

    @Test
    void prochainRound() {
    }

    @Test
    void getRoundActuel() {
    }

    @Test
    void getJoueurs() {
    }

    @Test
    void setJoueurs() {
    }

    @Test
    void getNumeroRoundActuel() {
    }

    @Test
    void setNumeroRoundActuel() {
    }

    @Test
    void getNombreRounds() {
    }

    @Test
    void setNombreRounds() {
    }

    @Test
    void getRoundTime() {
    }

    @Test
    void setRoundTime() {
    }

    @Test
    void getAdmin() {
    }

    @Test
    void setAdmin() {
    }

    @Test
    void setRounds() {
    }

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void miseAJourScore() {
        assertEquals(j1.getScore(), 0);
        assertEquals(j2.getScore(), 0);
        assertEquals(j3.getScore(), 0);

        partie.miseAJourScore(form);

        assertEquals(j1.getScore(), Partie.POINTS_REPONSE_UNIQUE + Partie.POINTS_BONNE_REPONSE);
        assertEquals(j2.getScore(), Partie.POINTS_REPONSE_UNIQUE + Partie.POINTS_BONNE_REPONSE);
        assertEquals(j3.getScore(), Partie.POINTS_REPONSE_UNIQUE + Partie.POINTS_BONNE_REPONSE);
    }

    @Test
    void getTableauOccurenceMotsParCategorie() {
        Map<Categorie, Map<String, Integer>> tab = partie.getTableauOccurenceMotsParCategorie(form);

        assertEquals(tab.get(Categorie.ANIMAL).get("FENNEC"), 3);

        assertEquals(tab.get(Categorie.VILLE).get("SAINT-JEAN-LES-BUZY"), 1);
        assertEquals(tab.get(Categorie.VILLE).get("FOIX"), 1);
        assertEquals(tab.get(Categorie.VILLE).get("TOURS"), 1);

        assertEquals(tab.get(Categorie.VEGETAL).get("FRAISE"), 1);
        assertEquals(tab.get(Categorie.PAYS).get("FINLANDE"), 1);
        assertEquals(tab.get(Categorie.METIER).get("COIFFEUR"), 1);

        assertNull(tab.get(Categorie.ANIMAL).get("CHÈVRE"));
    }

    @Test
    void creerRounds() {
    }

    @Test
    void reponseEstValide() {
        assertTrue(partie.reponseEstValide(Categorie.VILLE, "Foix"));
        assertFalse(partie.reponseEstValide(Categorie.VILLE, "Champagne"));
        assertTrue(partie.reponseEstValide(Categorie.PAYS, "fidji"));
        assertTrue(partie.reponseEstValide(Categorie.METIER, "FaCtEur"));

        assertFalse(partie.reponseEstValide(Categorie.PAYS, "fédéré"));

        partie.getRoundActuel().setLettre("I");

        assertTrue(partie.reponseEstValide(Categorie.PAYS, "Îles Mariannes du Nord"));
    }
}