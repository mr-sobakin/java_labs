package labs.lab3.vb.insurance;

import labs.lab3.vb.insurance.derivative.Derivative;
import labs.lab3.vb.insurance.model.HealthInsurance;
import labs.lab3.vb.insurance.model.LifeInsurance;
import labs.lab3.vb.insurance.model.PropertyInsurance;
import labs.lab3.vb.insurance.model.RiskLevel;
import labs.lab3.vb.insurance.model.*;
import labs.lab3.vb.insurance.storage.CsvStorage;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Path STORE = Path.of("insurance_data.csv");

    public static void main(String[] args) {
        Derivative derivative;
        try {
            derivative = CsvStorage.load(STORE);
        } catch (Exception e) {
            derivative = new Derivative();
        }

        // if empty, create sample data
        if (derivative.getObligations().isEmpty()) {
            seedSample(derivative);
        }

        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("=== Insurance Derivative Console ===");
            System.out.println("1 - List obligations");
            System.out.println("2 - Total premium");
            System.out.println("3 - Sort by risk (desc) and list");
            System.out.println("4 - Find by premium range");
            System.out.println("5 - Save to file");
            System.out.println("0 - Exit");
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.println(derivative);
                    break;
                case "2":
                    System.out.printf("Total premium: %.2f%n", derivative.totalPremium());
                    break;
                case "3":
                    derivative.sortByRiskDesc();
                    System.out.println("Sorted by risk (desc):");
                    System.out.println(derivative);
                    break;
                case "4":
                    System.out.print("Min premium: ");
                    double min = parseDouble(sc.nextLine(), 0.0);
                    System.out.print("Max premium: ");
                    double max = parseDouble(sc.nextLine(), Double.MAX_VALUE);
                    List<InsuranceObligation> found = derivative.findByPremiumRange(min, max);
                    System.out.println("Found obligations:");
                    found.forEach(System.out::println);
                    break;
                case "5":
                    try {
                        CsvStorage.save(derivative, STORE);
                        System.out.println("Saved to " + STORE.toAbsolutePath());
                    } catch (Exception e) {
                        System.out.println("Save failed: " + e.getMessage());
                    }
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Unknown choice");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void seedSample(Derivative d) {
        d.add(new LifeInsurance("L1", "Life - John", 100_000, 0.005, RiskLevel.MEDIUM, 45));
        d.add(new PropertyInsurance("P1", "House - Riverside", 200_000, 0.003, RiskLevel.HIGH, 300_000, 1.3));
        d.add(new HealthInsurance("H1", "Health - Anna", 50_000, 0.007, RiskLevel.LOW, 0));
        d.add(new LifeInsurance("L2", "Life - Senior", 80_000, 0.006, RiskLevel.HIGH, 70));
    }

    private static double parseDouble(String s, double fallback) {
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return fallback;
        }
    }
}
