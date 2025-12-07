package labs.lab4.v6.deposit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<Deposit> deposits = new ArrayList<>();
        System.out.println("Deposit management interactive console");
        boolean running = true;
        while (running) {
            printMenu();
            String choice = readLine("Choose option");
            try {
                switch (choice) {
                    case "1" -> deposits.add(createDeposit());
                    case "2" -> listDeposits(deposits);
                    case "3" -> calculateInterest(deposits);
                    case "4" -> changeDuration(deposits);
                    case "5" -> checkWithdrawable(deposits);
                    case "6" -> closeDeposit(deposits);
                    case "7" -> closeAndOpenNew(deposits);
                    case "0" -> {
                        running = false;
                        System.out.println("Exiting.");
                    }
                    default -> System.out.println("Unknown option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1 - Create deposit");
        System.out.println("2 - List deposits");
        System.out.println("3 - Calculate interest for months");
        System.out.println("4 - Change duration");
        System.out.println("5 - Check if withdrawable");
        System.out.println("6 - Close deposit");
        System.out.println("7 - Close and open new in another currency");
        System.out.println("0 - Exit");
    }

    private static Deposit createDeposit() {
        BigDecimal balance = new BigDecimal(readLine("Enter initial balance (e.g. 1000.00)"));
        Currency currency = readCurrency();
        boolean withdrawable = readYesNo("Is withdrawable? (y/n)");
        DurationType duration = readDuration();
        Deposit d = new BankDeposit(balance, currency, withdrawable, duration);
        System.out.println("Created: " + d.info());
        return d;
    }

    private static void listDeposits(List<Deposit> deposits) {
        if (deposits.isEmpty()) {
            System.out.println("No deposits.");
            return;
        }
        for (int i = 0; i < deposits.size(); i++) {
            System.out.printf("[%d] %s%n", i, deposits.get(i).info());
        }
    }

    private static void calculateInterest(List<Deposit> deposits) {
        Deposit d = chooseDeposit(deposits);
        if (d == null) return;
        int months = Integer.parseInt(readLine("Months to calculate"));
        BigDecimal interest = d.calculateInterestForMonths(months);
        System.out.println("Interest: " + interest + " " + d.getCurrency());
    }

    private static void changeDuration(List<Deposit> deposits) {
        Deposit d = chooseDeposit(deposits);
        if (d == null) return;
        DurationType newDur = readDuration();
        d.changeDuration(newDur);
        System.out.println("Updated: " + d.info());
    }

    private static void checkWithdrawable(List<Deposit> deposits) {
        Deposit d = chooseDeposit(deposits);
        if (d == null) return;
        System.out.println("Withdrawable: " + d.isWithdrawable());
    }

    private static void closeDeposit(List<Deposit> deposits) {
        Deposit d = chooseDeposit(deposits);
        if (d == null) return;
        d.close();
        System.out.println("Closed: " + d.info());
    }

    private static void closeAndOpenNew(List<Deposit> deposits) {
        Deposit d = chooseDeposit(deposits);
        if (d == null) return;
        Currency newCur = readCurrency();
        Deposit opened = d.closeAndOpenNew(newCur);
        deposits.add(opened);
        System.out.println("Old: " + d.info());
        System.out.println("New: " + opened.info());
    }

    private static Deposit chooseDeposit(List<Deposit> deposits) {
        if (deposits.isEmpty()) {
            System.out.println("No deposits.");
            return null;
        }
        listDeposits(deposits);
        String s = readLine("Enter deposit index");
        int idx;
        try {
            idx = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            System.out.println("Invalid index.");
            return null;
        }
        if (idx < 0 || idx >= deposits.size()) {
            System.out.println("Index out of range.");
            return null;
        }
        return deposits.get(idx);
    }

    private static Currency readCurrency() {
        while (true) {
            String in = readLine("Currency (USD/EUR/RUB)").toUpperCase(Locale.ROOT);
            try {
                return Currency.valueOf(in);
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown currency.");
            }
        }
    }

    private static DurationType readDuration() {
        while (true) {
            String in = readLine("Duration (PERPETUAL/LONG_TERM/SHORT_TERM)");
            try {
                return DurationType.valueOf(in.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown duration.");
            }
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            String in = readLine(prompt).trim().toLowerCase(Locale.ROOT);
            if (in.equals("y") || in.equals("yes")) return true;
            if (in.equals("n") || in.equals("no")) return false;
            System.out.println("Please enter y or n.");
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }
}
