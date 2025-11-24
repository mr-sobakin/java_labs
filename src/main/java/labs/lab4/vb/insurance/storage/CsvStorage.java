package labs.lab4.vb.insurance.storage;

import labs.lab4.vb.insurance.derivative.Derivative;
import labs.lab4.vb.insurance.model.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvStorage {
    // Simple CSV format: type,id,description,insuredSum,baseRate,risk,other...
    public static void save(Derivative d, Path path) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (InsuranceObligation o : d.getObligations()) {
            if (o instanceof LifeInsurance) {
                LifeInsurance l = (LifeInsurance) o;
                sb.append(String.join(",",
                        "LIFE", l.getId(), escape(l.getDescription()), Double.toString(l.getInsuredSum()),
                        Double.toString(l.getBasePremiumRate()), l.getRiskLevel().name(), Integer.toString(l.getInsuredAge())
                )).append(System.lineSeparator());
            } else if (o instanceof PropertyInsurance) {
                PropertyInsurance p = (PropertyInsurance) o;
                sb.append(String.join(",",
                        "PROPERTY", p.getId(), escape(p.getDescription()), Double.toString(p.getInsuredSum()),
                        Double.toString(p.getBasePremiumRate()), p.getRiskLevel().name(),
                        Double.toString(p.getPropertyValue()), Double.toString(p.getLocationRiskFactor())
                )).append(System.lineSeparator());
            } else if (o instanceof HealthInsurance) {
                HealthInsurance h = (HealthInsurance) o;
                sb.append(String.join(",",
                        "HEALTH", h.getId(), escape(h.getDescription()), Double.toString(h.getInsuredSum()),
                        Double.toString(h.getBasePremiumRate()), h.getRiskLevel().name(), Integer.toString(h.getPreExistingConditions())
                )).append(System.lineSeparator());
            }
        }
        Files.write(path, sb.toString().getBytes());
    }

    public static Derivative load(Path path) throws IOException {
        Derivative d = new Derivative();
        if (!Files.exists(path)) {
            return d;
        }
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",", -1);
            String type = parts[0].trim();
            try {
                switch (type) {
                    case "LIFE": {
                        String id = parts[1];
                        String desc = unescape(parts[2]);
                        double insured = Double.parseDouble(parts[3]);
                        double rate = Double.parseDouble(parts[4]);
                        RiskLevel r = RiskLevel.valueOf(parts[5]);
                        int age = Integer.parseInt(parts[6]);
                        d.add(new LifeInsurance(id, desc, insured, rate, r, age));
                        break;
                    }
                    case "PROPERTY": {
                        String id = parts[1];
                        String desc = unescape(parts[2]);
                        double insured = Double.parseDouble(parts[3]);
                        double rate = Double.parseDouble(parts[4]);
                        RiskLevel r = RiskLevel.valueOf(parts[5]);
                        double propValue = Double.parseDouble(parts[6]);
                        double loc = Double.parseDouble(parts[7]);
                        d.add(new PropertyInsurance(id, desc, insured, rate, r, propValue, loc));
                        break;
                    }
                    case "HEALTH": {
                        String id = parts[1];
                        String desc = unescape(parts[2]);
                        double insured = Double.parseDouble(parts[3]);
                        double rate = Double.parseDouble(parts[4]);
                        RiskLevel r = RiskLevel.valueOf(parts[5]);
                        int cond = Integer.parseInt(parts[6]);
                        d.add(new HealthInsurance(id, desc, insured, rate, r, cond));
                        break;
                    }
                    default:
                        // ignore unknown
                }
            } catch (Exception ex) {
                // skip malformed line
            }
        }
        return d;
    }

    private static String escape(String s) {
        return s.replace(",", "\\,");
    }

    private static String unescape(String s) {
        return s.replace("\\,", ",");
    }
}
