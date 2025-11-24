package labs.lab4.vb.insurance.model;

public abstract class InsuranceObligation {
    private final String id;
    private String description;
    private double insuredSum;
    private double basePremiumRate; // fraction, e.g. 0.02 = 2%
    private RiskLevel riskLevel;

    protected InsuranceObligation(String id, String description, double insuredSum, double basePremiumRate, RiskLevel riskLevel) {
        this.id = id;
        this.description = description;
        this.insuredSum = insuredSum;
        this.basePremiumRate = basePremiumRate;
        this.riskLevel = riskLevel;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getInsuredSum() {
        return insuredSum;
    }

    public double getBasePremiumRate() {
        return basePremiumRate;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInsuredSum(double insuredSum) {
        this.insuredSum = insuredSum;
    }

    public void setBasePremiumRate(double basePremiumRate) {
        this.basePremiumRate = basePremiumRate;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    // polymorphic premium calculation
    public abstract double calculatePremium();

    @Override
    public String toString() {
        return String.format("%s[id=%s, insured=%.2f, baseRate=%.4f, risk=%s]",
                this.getClass().getSimpleName(), id, insuredSum, basePremiumRate, riskLevel);
    }
}
