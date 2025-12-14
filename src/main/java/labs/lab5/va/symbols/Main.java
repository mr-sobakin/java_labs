
package labs.lab5.va.symbols;

import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        String text = null;

        while (true) {
            System.out.println("Menu:");
            System.out.println("1) Enter text manually");
            System.out.println("2) Analyze current text");
            System.out.println("3) Exit");
            System.out.print("Choose an option (1-3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.println("Enter text. Finish input with a single line containing only `.end`:");
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String line = scanner.nextLine();
                        if (line != null && line.equals(".end")) break;
                        sb.append(line).append("\n");
                    }
                    text = sb.toString().trim();
                    System.out.println("Text stored (" + (text.isBlank() ? 0 : text.length()) + " chars).");
                }
                case "2" -> {
                    if (text == null || text.isBlank()) {
                        System.out.println("No text available. Enter text first.");
                    } else {
                        analyzeAndPrint(text);
                    }
                }
                case "3" -> {
                    System.out.println("Exiting.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Enter 1-3.");
            }

            System.out.println();
        }
    }

    private static void analyzeAndPrint(String text) {
        BreakIterator bi = BreakIterator.getSentenceInstance(Locale.getDefault());
        bi.setText(text);
        int start = bi.first();
        int totalSentences = 0;
        int moreVowels = 0;
        int moreConsonants = 0;
        int equal = 0;

        for (int end = bi.next(); end != BreakIterator.DONE; start = end, end = bi.next()) {
            String sentence = text.substring(start, end).trim();
            if (sentence.isEmpty()) continue;
            totalSentences++;
            SentenceAnalyzer.Analysis a = SentenceAnalyzer.analyze(sentence);
            switch (a.verdict) {
                case MORE_VOWELS -> moreVowels++;
                case MORE_CONSONANTS -> moreConsonants++;
                case EQUAL -> equal++;
            }
            String verdictStr = switch (a.verdict) {
                case MORE_VOWELS -> "More vowels";
                case MORE_CONSONANTS -> "More consonants";
                default -> "Equal vowels and consonants";
            };
            System.out.println("Sentence: " + sentence);
            System.out.println("Vowels: " + a.vowels + ", Consonants: " + a.consonants + " -> " + verdictStr);
            System.out.println();
        }

        System.out.println("Summary:");
        System.out.println("Total sentences: " + totalSentences);
        System.out.println("Sentences with more vowels: " + moreVowels);
        System.out.println("Sentences with more consonants: " + moreConsonants);
        System.out.println("Sentences with equal counts: " + equal);
    }
}
