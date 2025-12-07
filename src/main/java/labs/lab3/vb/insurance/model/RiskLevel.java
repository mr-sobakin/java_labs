package labs.lab3.vb.insurance.model;

public enum RiskLevel {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int score;

    RiskLevel(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
