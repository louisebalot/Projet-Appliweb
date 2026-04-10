package n7.facade;

import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import ch.qos.logback.core.joran.sanity.Pair;

public class Partie {
    /** Tableau des lettres disponibles */
    private char[] lettresDisponibles = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    /** Collection des joueurs de la partie */
    private Collection<Joueur> joueurs;

    /** Admin de la partie */
    private Joueur admin;

    /** Liste des rounds de la partie */
    private Set<Pair<Integer, Character>> rounds;

    /** Numéro du round actuel */
    private int currentRoundNumber;

    /** Tableau des scores de chaque joueur */
    private Set<Pair<Joueur, Integer>> scores;

    /** Nombre de rounds à jouer */
    private int nombreRounds;

    /** Temps en seconde d'un round */
    private int roundTime;

    /** Secondes écoulées depuis le début du round */
    private int timeSinceRoundStart;

    /** Ne pas utiliser */
    public Partie() {
        // Ne pas utiliser
    }

    /**
     * Crée une partie à partir d'un admin.
     * 
     * @param admin        Admin de la partie
     * @param nombreRounds Nombre de rounds de la partie.
     * @param roundTime    Temps en seconde pour répondre.
     */
    public Partie(Joueur admin, int nombreRounds, int roundTime) {
        this.joueurs = new Vector<>();
        this.rounds = new HashSet<>();
        this.currentRoundNumber = 0;
        this.scores = new HashSet<>();
        this.timeSinceRoundStart = -1;

        this.admin = admin;
        this.nombreRounds = nombreRounds;
        this.roundTime = roundTime;
    }

    /**
     * Passe au prochain round.
     * 
     * Incrémente le numéro du round puis le renvoie.
     * 
     * @return le numéro du round suivant
     */
    public int nextRound() {
        return 0;
    }

    // Setters and Getters
    public Collection<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(Collection<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Joueur getAdmin() {
        return admin;
    }

    public void setAdmin(Joueur admin) {
        this.admin = admin;
    }
}