package pack;

public enum Categorie {
    PAYS("table_pays"),
    VILLE("table_villes"),
    PRENOM("table_prenoms"),
    COULEUR("table_couleurs"),
    VEGETAL("table_vegetaux"),
    ANIMAL("table_animaux"),
    METIER("table_metiers"),
    SPORT("table_sports");

    private final String nomTable;

    Categorie(String nomTable) {
        this.nomTable = nomTable;
    }

    public String getNomTable() {
        return this.nomTable;
    }
}