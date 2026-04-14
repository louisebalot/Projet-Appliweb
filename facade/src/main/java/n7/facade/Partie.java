package n7.facade;

import java.util.Random;

import java.util.Vector;

import java.util.List;

public class Partie {
    public enum Categorie {
        PAYS, VILLE, PRENOM, COULEUR, VEGETAL, ANIMAL, METIER, SPORT
    }
    
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String lettresDisponibles = "";

    /** Collection des joueurs de la partie */
    private List<Joueur> joueurs;

    /** Admin de la partie */
    private Joueur admin;

    /** Liste des rounds de la partie */
    private List<Round> rounds;

    /** Numéro du round actuel */
    private int numeroRoundActuel;

    /** Nombre de rounds à jouer */
    private int nombreRounds;

    /** Temps pour répondre. */
    private int roundTime;

    private int id;

    /**  */
    private Random random = new Random();

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
    public Partie(Joueur admin) {
        this.joueurs = new Vector<>();
        this.rounds = new Vector<>();
        this.numeroRoundActuel = 0;

        this.admin = admin;
        this.id = 0;
    }

    /**
     * Ajoute un joueur dans une partie s'il n'est pas dedans.
     * 
     * @param nouveauJoueur Joueur à ajouter
     */
    public void ajouterJoueur(Joueur nouveauJoueur) {
        if (!joueurs.contains(nouveauJoueur)) {
            joueurs.add(nouveauJoueur);
        }
    }

    /**
     * Passe au prochain round.
     */
    public int prochainRound() {
        return numeroRoundActuel++;
    }

    public Round getRoundActuel() {
        return rounds.get(numeroRoundActuel);
    }

    // Setters and getters
    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }

    public Joueur getAdmin() {
        return admin;
    }

    public void setAdmin(Joueur admin) {
        this.admin = admin;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void creerRounds(int nombreRounds, int roundTime) {
        this.rounds = new Vector<>();
        this.nombreRounds = nombreRounds;
        this.roundTime = roundTime;
        // Initialiser les rounds
        for (int i = 1; i < nombreRounds; i++) {

            // Vérifier qu'il reste des lettres disponibles
            if (lettresDisponibles.isEmpty()) {
                lettresDisponibles = ALPHABET;
            }

            // Choisir une lettre
            int indexLettre = random.nextInt(lettresDisponibles.length());
            char lettreChoisie = lettresDisponibles.charAt(indexLettre);

            // Supprimer la lettre des choix disponibles
            lettresDisponibles = lettresDisponibles.substring(0, indexLettre)
                    + lettresDisponibles.substring(indexLettre + 1);

            // Créer le round
            rounds.add(new Round(this, i, lettreChoisie, roundTime));
        }
        this.setRounds(rounds);
    }

}