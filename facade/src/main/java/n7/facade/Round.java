package n7.facade;

import java.time.LocalTime;

public class Round {
    /** Numero du round (sera peut-être remplacé par une ID) */
    private int number;

    /** Lettre du round */
    private char lettre;

    /** Partie liée au round. */
    private Partie partie;

    /** Temps maximum du round */
    private int tempsMaxRound;

    /** Temps auquel le round à commencé */
    private LocalTime roundStartTime;

    /** Ne pas utiliser */
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