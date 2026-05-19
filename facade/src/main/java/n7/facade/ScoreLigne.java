package n7.facade;

public class ScoreLigne {
    private String pseudo;
    private int score;

    public ScoreLigne() {
    }

    public ScoreLigne(String pseudo, int score) {
        this.pseudo = pseudo;
        this.score = score;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getScore() {
        return score;
    }
}