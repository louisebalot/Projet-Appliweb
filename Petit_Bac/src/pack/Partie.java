package pack;

import pack.outils.StringNormalizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Partie {
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Points attribués selon la réponse.
     * 5 pour une reponse qui se trouve dans notre dase de donnée
     * 10 s'il est le seul à avoir choisi ce mot
     */
    public static final int
            POINTS_REPONSE_UNIQUE = 10,
            POINTS_BONNE_REPONSE = 5;
    public String lettresDisponibles = "";
    /**
     * Collection des joueurs de la partie
     */
    private List<Joueur> joueurs;
    /**
     * Admin de la partie
     */
    private Joueur admin;
    /**
     * Liste des rounds de la partie
     */
    private List<Round> rounds;
    /**
     * Numéro du round actuel
     */
    private int numeroRoundActuel;
    /**
     * Nombre de rounds à jouer
     */
    private int nombreRounds;
    /**
     * Temps pour répondre.
     */
    private int roundTime;
    private int id;
    /**
     *
     */
    private Random random = new Random();

    /**
     * Ne pas utiliser
     */
    public Partie() {
        // Ne pas utiliser
    }

    /**
     * Crée une partie à partir d'un admin.
     *
     * @param admin Admin de la partie
     */
    public Partie(Joueur admin) {


        this.joueurs = new Vector<>();
        this.joueurs.add(admin);
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

    public int getNumeroRoundActuel() {
        return numeroRoundActuel;
    }

    public void setNumeroRoundActuel(int numeroRoundActuel) {
        this.numeroRoundActuel = numeroRoundActuel;
    }

    public int getNombreRounds() {
        return nombreRounds;
    }

    public void setNombreRounds(int nombreRounds) {
        this.nombreRounds = nombreRounds;
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
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

    /**
     * Mettre à jour le tableau des scores en fonction des réponses données dans un formulaire.
     *
     * @param form formulaire des réponses.
     */
    public void miseAJourScore(Formulaire form) {
        Map<Categorie, Map<String, Integer>> tableauOccurences = getTableauOccurenceMotsParCategorie(form);

        for (Joueur j : joueurs) {
            for (Categorie cat : Categorie.values()) {
                // Récupérer le nombre d'occurence de la réponse du joueur
                String reponse = form.getReponseJoueur(j, cat);
                if (reponse != null && reponseEstValide(cat, reponse)) {
                    // S'il a répondu on ajoute les points selon si la réponse est unique ou non.
                    int occurenceReponse = tableauOccurences.get(cat).get(reponse.toUpperCase());
                    j.ajouterScore(occurenceReponse > 1 ? POINTS_BONNE_REPONSE : POINTS_REPONSE_UNIQUE);
                }
            }
        }
    }

    /**
     * Obtenir L'occurence de chaque mot pour chaque catégorie.
     * <p>
     * Les mots sont normalisés selon la fonction {@link pack.outils.StringNormalizer#normaliserString(String)}
     *
     * <p>
     * Exemple pour 2 joueurs et 2 catégories {@code VILLE} et {@code VEGETAL} :
     * </p>
     *
     * <p>
     * Réponses joueur 1 :
     * {@code VILLE} : Limoges, {@code VEGETAL} : Carotte <br>
     * Réponses joueur 2 :
     * {@code VILLE} : Tours,   {@code VEGETAL} : Carotte <br>
     * </p>
     * <p>
     * Alors pour la catégorie {@code VILLE} on a "LIMOGES" avec une occurrence de {@code 1} et "TOURS"
     * avec une occurrence de {@code 1}. <br>
     * Pour la catégorie {@code VEGETAL} on a "CAROTTE" avec une occurrence de {@code 2}
     *
     * @param form formulaire dont on veut les occurences des mots selon leur catégorie
     * @return Le tableau d'occurrence de chaque mot selon la catégorie
     */
    public Map<Categorie, Map<String, Integer>> getTableauOccurenceMotsParCategorie(Formulaire form) {
        Map<Categorie, Map<String, Integer>> tableau = new HashMap<>();

        for (Categorie cat : Categorie.values()) {
            Map<String, Integer> tableauCategorie = new HashMap<>();
            // Pour chaque réponse donnée d'une catégorie
            for (String reponse : form.getReponsesAUneCategorie(cat)) {

                if (reponse != null) {
                    // new Scanner(new File("../db/" + cat.getNomFichierDb()))
                    //                            .useDelimiter("\\Z").next().contains(reponse)
                    // TODO : TEMPORAIRE (le temps d'installer les vraies DB)
                    // Si le mot est dans le fichier DB, on le compte
                    reponse = StringNormalizer.normaliserString(reponse);
                    if (!tableauCategorie.containsKey(reponse)) {
                        tableauCategorie.put(reponse, 1);
                    } else {
                        tableauCategorie.put(reponse, tableauCategorie.get(reponse) + 1);
                    }
                }


            }

            tableau.put(cat, tableauCategorie);
        }

        return tableau;
    }

    /**
     * Indique si la réponse est valide.
     *
     * <p>
     * Fait un appel à la "base de donnée" pour savoir si la réponse est bien présente à l'intérieur.
     * La vérification ne prend pas la case ni les accents en compte.
     * </p>
     *
     * @param categorie catégorie de la réponse
     * @param reponse   réponse à vérifier
     * @return vrai si la réponse est valide, faux sinon
     */
    public boolean reponseEstValide(Categorie categorie, String reponse) {
        if (reponse == null) return false;

        String upperReponse = StringNormalizer.normaliserString(reponse);
        // Vérifier que la première lettre soit la bonne
        boolean res = upperReponse.startsWith(rounds.get(numeroRoundActuel).getLettre());
        try {
            // Illisible mais permet de savoir si le mot cherché est dans le fichier
            res &= StringNormalizer.normaliserString(new Scanner(new File("../db/" + categorie.getNomFichierDb()))
                    .useDelimiter("\\Z").next()).contains("'" + upperReponse.toUpperCase());
        } catch (FileNotFoundException e) {
            res = false;
            e.printStackTrace();
        }

        return res;
    }

    public void creerRounds(int nombreRounds, int roundTime) {
        this.rounds = new Vector<>();
        this.nombreRounds = nombreRounds;
        this.roundTime = roundTime;
        // Initialiser les rounds
        for (int i = 1; i <= nombreRounds; i++) {

            // Vérifier qu'il reste des lettres disponibles
            if (lettresDisponibles.isEmpty()) {
                lettresDisponibles = ALPHABET;
            }

            // Choisir une lettre
            int indexLettre = random.nextInt(lettresDisponibles.length());
            String lettreChoisie = Character.toString(lettresDisponibles.charAt(indexLettre));

            // Supprimer la lettre des choix disponibles
            lettresDisponibles = lettresDisponibles.substring(0, indexLettre)
                    + lettresDisponibles.substring(indexLettre + 1);

            // Créer le round
            rounds.add(new Round(this, i, lettreChoisie, roundTime));
        }

        this.setRounds(rounds);
    }
}