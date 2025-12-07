package labs.lab3.vb.insurance.model;

public class LifeInsurance extends InsuranceObligation {
    private int insuredAge;

    public LifeInsurance(String id, String description, double insuredSum, double basePremiumRate, RiskLevel riskLevel, int insuredAge) {
        super(id, description, insuredSum, basePremiumRate, riskLevel);
        this.insuredAge = insuredAge;
    }

    public int getInsuredAge() {
        return insuredAge;
    }

    public void setInsuredAge(int insuredAge) {
        this.insuredAge = insuredAge;
    }

    @Override
    public double calculatePremium() {
        double ageFactor = 1.0;
        if (insuredAge > 60) {
            ageFactor = 1.6;
        } else if (insuredAge > 40) {
            ageFactor = 1.2;
        } else if (insuredAge > 25) {
            ageFactor = 1.05;
        }
        double riskMultiplier = 1.0 + 0.5 * (getRiskLevel().getScore() - 1);
        return getInsuredSum() * getBasePremiumRate() * ageFactor * riskMultiplier;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", age=%d, premium=%.2f", insuredAge, calculatePremium());
    }
}
