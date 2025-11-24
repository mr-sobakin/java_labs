package labs.lab4.vb.insurance.model;

public class PropertyInsurance extends InsuranceObligation {
    private double propertyValue;
    private double locationRiskFactor; // e.g. 1.0 normal, >1 higher risk

    public PropertyInsurance(String id, String description, double insuredSum, double basePremiumRate, RiskLevel riskLevel,
                             double propertyValue, double locationRiskFactor) {
        super(id, description, insuredSum, basePremiumRate, riskLevel);
        this.propertyValue = propertyValue;
        this.locationRiskFactor = locationRiskFactor;
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    public double getLocationRiskFactor() {
        return locationRiskFactor;
    }

    public void setLocationRiskFactor(double locationRiskFactor) {
        this.locationRiskFactor = locationRiskFactor;
    }

    @Override
    public double calculatePremium() {
        double valueFactor = Math.min(2.0, 1.0 + propertyValue / 1_000_000.0); // larger properties slightly higher factor
        double riskMultiplier = 1.0 + 0.6 * (getRiskLevel().getScore() - 1);
        return getInsuredSum() * getBasePremiumRate() * valueFactor * locationRiskFactor * riskMultiplier;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", property=%.2f, locRisk=%.2f, premium=%.2f", propertyValue, locationRiskFactor, calculatePremium());
    }
}
