package labs.lab4.vb.insurance.model;

public class HealthInsurance extends InsuranceObligation {
    private int preExistingConditions;

    public HealthInsurance(String id, String description, double insuredSum, double basePremiumRate, RiskLevel riskLevel,
                           int preExistingConditions) {
        super(id, description, insuredSum, basePremiumRate, riskLevel);
        this.preExistingConditions = preExistingConditions;
    }

    public int getPreExistingConditions() {
        return preExistingConditions;
    }

    public void setPreExistingConditions(int preExistingConditions) {
        this.preExistingConditions = preExistingConditions;
    }

    @Override
    public double calculatePremium() {
        double condFactor = 1.0 + 0.25 * preExistingConditions;
        double riskMultiplier = 1.0 + 0.4 * (getRiskLevel().getScore() - 1);
        return getInsuredSum() * getBasePremiumRate() * condFactor * riskMultiplier;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", conditions=%d, premium=%.2f", preExistingConditions, calculatePremium());
    }
}
