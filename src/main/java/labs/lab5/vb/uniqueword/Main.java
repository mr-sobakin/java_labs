package labs.lab5.vb.uniqueword;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Choose input: 1 - paste text (end with a single line: END), 2 - demo sample texts");
        String mode = br.readLine().trim();

        String[] samples = new String[] {
                "This   is a sample sentence (remove this part)  with extra   spaces. Another sentence!",
                "Here is a paragraph with a listing:\n\tint x = 1;\n\tSystem.out.println(x);\nThe paragraph continues after listing.",
                "She said: \"Find the longest \"quoted\" part\" and then moved on. End.",
                "Example? Yes! Keep [this] and remove [that and more]."
        };

        String inputText = null;
        boolean multiple = false;

        if ("2".equals(mode)) {
            System.out.println("Demo samples:");
            for (int i = 0; i < samples.length; i++) {
                String preview = samples[i].replaceAll("\\r?\\n", " ¶ ");
                if (preview.length() > 80) preview = preview.substring(0, 77) + "...";
                System.out.printf("%d) %s%n", i + 1, preview);
            }
            System.out.println("Enter sample number to run or 'all' to run all samples:");
            String sel = br.readLine().trim();
            if ("all".equalsIgnoreCase(sel)) {
                multiple = true;
            } else {
                try {
                    int idx = Integer.parseInt(sel) - 1;
                    if (idx < 0 || idx >= samples.length) {
                        System.out.println("Invalid selection. Exiting.");
                        return;
                    }
                    inputText = samples[idx];
                } catch (NumberFormatException e) {
                    System.out.println("Invalid selection. Exiting.");
                    return;
                }
            }
        } else {
            System.out.println("Paste text lines. End with a single line: END");
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                if ("END".equals(line)) break;
                text.append(line).append(System.lineSeparator());
            }
            inputText = text.toString();
        }

        System.out.println("Enter start character:");
        String sStart = br.readLine();
        char start = (sStart == null || sStart.isEmpty()) ? '\0' : sStart.charAt(0);

        System.out.println("Enter end character:");
        String sEnd = br.readLine();
        char end = (sEnd == null || sEnd.isEmpty()) ? '\0' : sEnd.charAt(0);

        if (multiple) {
            for (int i = 0; i < samples.length; i++) {
                System.out.println(System.lineSeparator() + "---- Sample " + (i + 1) + " ORIGINAL ----");
                System.out.println(samples[i]);
                String result = TextProcessor.process(samples[i], start, end);
                System.out.println(System.lineSeparator() + "---- Sample " + (i + 1) + " PROCESSED ----");
                System.out.println(result);
            }
        } else {
            String result = TextProcessor.process(inputText, start, end);
            System.out.println(System.lineSeparator() + "Processed text:");
            System.out.println(result);
        }
    }
}
