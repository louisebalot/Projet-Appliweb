package pack;

public enum Categorie {
    PAYS("pays", "table_pays"),
    VILLE("villes", "table_villes"),
    PRENOM("prenoms", "table_prenoms"),
    COULEUR("couleurs", "table_couleurs"),
    VEGETAL("vegetaux", "table_vegetaux"),
    ANIMAL("animaux", "table_animaux"),
    METIER("metiers", "table_metiers"),
    SPORT("sports", "table_sports");

    private final String nomFichierDb;
    private final String nomTable;

    Categorie(String nomFichierDb, String nomTable) {
        this.nomFichierDb = nomFichierDb + ".sql";
        this.nomTable = nomTable;
    }

    /**
     * Obtenir le nom du fichier lié à la base de donnée de la catégorie.
     *
     * @return lom du fichier .sql de la catégorie
     */
    public String getNomFichierDb() {
        return nomFichierDb;
    }

    public String getNomTable() {
        return this.nomTable;
    }
}