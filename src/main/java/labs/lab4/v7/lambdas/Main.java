package labs.lab4.v7.lambdas;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Default MAX_WORDS (M): " + TextProcessor.MAX_WORDS);
        demo("one two three four", 2, 100);

        demo("alpha beta gamma delta epsilon", 3, 30);

        TextProcessor.MAX_WORDS = 5;
        System.out.println("Changed MAX_WORDS (M): " + TextProcessor.MAX_WORDS);
        demo("hello world sample text", 5, 100);

        demo("   spaced   words   example  ", 2, 40);

        demo("word", 10, 1);

        demo("", 3, 10);

        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("Enter text (or 'exit' to quit): ");
            String text = input.nextLine();
            if (text.equalsIgnoreCase("exit")) break;

            System.out.println("Enter number of copies: ");
            int copies = Integer.parseInt(input.nextLine());

            System.out.println("Enter character limit: ");
            int limit = Integer.parseInt(input.nextLine());

            demo(text, copies, limit);
        }
    }

    private static void demo(String text, int copies, int limit) {
        String out = TextProcessor.PROCESSOR.apply(text, copies, limit);
        int wordsProduced = countCommas(out);
        System.out.println("Input: `" + text + "`");
        System.out.println("Copies: " + copies + ", Limit: " + limit);
        System.out.println("Output: `" + out + "`");
        System.out.println("Words produced (M used): " + wordsProduced);
        System.out.println("Output length: " + out.length());
        System.out.println("-----");
    }

    private static int countCommas(String s) {
        if (s == null || s.isEmpty()) return 0;
        int count = 0;
        for (char ch : s.toCharArray()) if (ch == ',') count++;
        return count;
    }
}
