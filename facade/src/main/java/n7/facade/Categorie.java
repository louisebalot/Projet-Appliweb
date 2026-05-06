package n7.facade;

public enum Categorie {
    PAYS("pays"),
    VILLE("villes"),
    PRENOM("prenoms"),
    COULEUR("couleurs"),
    VEGETAL("vegetaux"),
    ANIMAL("animaux"),
    METIER("metiers"),
    SPORT("sports");

    private final String nomFichierDb;

    Categorie(String nomFichierDb) {
        this.nomFichierDb = nomFichierDb + ".sql";
    }

    /**
     * Obtenir le nom du fichier lié à la base de donnée de la catégorie.
     *
     * @return lom du fichier .sql de la catégorie
     */
    public String getNomFichierDb() {
        return nomFichierDb;
    }
}