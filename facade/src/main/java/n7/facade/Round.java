package n7.facade;

//import java.time.LocalTime;

import static java.lang.Integer.max;

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
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    /*public LocalTime getRoundStartTime() {
        return roundStartTime;
    }

    public void setRoundStartTime(LocalTime roundStartTime) {
        this.roundStartTime = roundStartTime;
    }*/

    /**
     * Commencer le round.
     */
    /* 
    public void start() {
        roundStartTime = LocalTime.now();
    }*/

    /**
     * Obtenir le nombre de secondes restantes du round.
     * <p>
     * valeur entre {@code 0} et {@code tempsMaxRound} inclus
     *
     * @return le nombre de secondes restantes pour jouer le round
     */
    /*public int getSecondesRestantes() {
        int tempsActuel = LocalTime.now().toSecondOfDay();
        int tempsDebutRound = roundStartTime.toSecondOfDay();

        // Détecter si le round à débuté le jour d'avant
        if (tempsActuel < tempsDebutRound) {
            tempsDebutRound -= SECONDES_DANS_UN_JOUR;
        }

        // Retourner la différence entre le temps maximum du round et le temps écoulé
        return max(0, tempsMaxRound - (tempsActuel - tempsDebutRound));
    }*/
}