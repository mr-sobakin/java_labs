package labs.lab3.vb.insurance.derivative;

import labs.lab3.vb.insurance.model.InsuranceObligation;
import labs.lab3.vb.insurance.model.RiskLevel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Derivative {
    private final List<InsuranceObligation> obligations = new ArrayList<>();

    public void add(InsuranceObligation o) {
        obligations.add(o);
    }

    public List<InsuranceObligation> getObligations() {
        return new ArrayList<>(obligations);
    }

    public double totalPremium() {
        return obligations.stream().mapToDouble(InsuranceObligation::calculatePremium).sum();
    }

    public void sortByRiskDesc() {
        obligations.sort(Comparator.comparingInt((InsuranceObligation o) -> o.getRiskLevel().getScore()).reversed());
    }

    public List<InsuranceObligation> findByPremiumRange(double min, double max) {
        return obligations.stream()
                .filter(o -> {
                    double p = o.calculatePremium();
                    return p >= min && p <= max;
                })
                .collect(Collectors.toList());
    }

    public List<InsuranceObligation> findByRisk(RiskLevel level) {
        return obligations.stream()
                .filter(o -> o.getRiskLevel() == level)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (InsuranceObligation o : obligations) {
            sb.append(o.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
