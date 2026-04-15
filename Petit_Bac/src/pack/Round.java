package pack;

import java.time.LocalTime;

public class Round {
    /**
     * Numero du round (sera peut-être remplacé par une ID)
     */
    private int number;

    /**
     * Lettre du round
     */
    private char lettre;

    /**
     * Partie liée au round.
     */
    private Partie partie;

    /**
     * Temps maximum du round
     */
    private int tempsMaxRound;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public char getLettre() {
        return lettre;
    }

    public void setLettre(char lettre) {
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

    public LocalTime getRoundStartTime() {
        return roundStartTime;
    }

    public void setRoundStartTime(LocalTime roundStartTime) {
        this.roundStartTime = roundStartTime;
    }

    /**
     * Temps auquel le round à commencé
     */
    private LocalTime roundStartTime;

    /**
     * Ne pas utiliser
     */
    public Round() {
        /** Ne pas utiliser */
    }

    public Round(Partie partie, int number, char lettre, int tempsMaxRound) {
        this.partie = partie;
        this.number = number;
        this.lettre = lettre;
        this.tempsMaxRound = tempsMaxRound;
    }

    /**
     * Commencer le round.
     */
    public void start() {
        roundStartTime = LocalTime.now();
    }
}