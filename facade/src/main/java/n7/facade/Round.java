package n7.facade;

//import java.time.LocalTime;

public class Round {
    /**
     * Nombre de secondes dans une journée.
     */
    private static final int SECONDES_DANS_UN_JOUR = 24 * 60 * 60;

    /**
     * Numero du round (sera peut-être remplacé par une ID)
     */
    private int number;

    /**
     * Lettre du round
     */
    private String lettre;

    /**
     * Partie liée au round.
     */
    private Partie partie;

    private Formulaire formulaire;

    /**
     * Temps maximum du round
     */
    private int tempsMaxRound;
    /**
     * Temps auquel le round à commencé
     */
    //public LocalTime roundStartTime;

    /**
     * Ne pas utiliser
     */
    public Round() {
        /** Ne pas utiliser */
    }

    public Round(Partie partie, int number, String lettre, int tempsMaxRound) {
        this.partie = partie;
        this.number = number;
        this.lettre = lettre;
        this.tempsMaxRound = tempsMaxRound;
        this.formulaire = new Formulaire();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Formulaire getFormulaire() {
        return formulaire;
    }

    public String getLettre() {
        return lettre;
    }

    public void setLettre(String lettre) {
        this.lettre = lettre;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    public int getTempsMaxRound() {
        return tempsMaxRound;
    }

    public void setTempsMaxRound(int tempsMaxRound) {
        this.tempsMaxRound = tempsMaxRound;
    }
}