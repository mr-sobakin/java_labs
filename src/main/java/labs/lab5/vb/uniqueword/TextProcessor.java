package labs.lab5.vb.uniqueword;

import java.util.ArrayList;
import java.util.List;

public class TextProcessor {
    public static String normalizeLine(String line) {
        if (line == null) return null;
        String t = line.replace('\t', ' ');
        return t.replaceAll(" {2,}", " ").trim();
    }

    public static List<Paragraph> parseParagraphs(String text) {
        String[] rawParas = text.split("\\r?\\n\\s*\\r?\\n");
        List<Paragraph> result = new ArrayList<>();
        for (String raw : rawParas) {
            Paragraph p = new Paragraph();
            String[] lines = raw.split("\\r?\\n");
            StringBuilder listingBuf = new StringBuilder();
            boolean inListing = false;
            for (String line : lines) {
                boolean isListingLine = line.startsWith("\t") || line.startsWith("    ");
                if (isListingLine) {
                    if (!inListing) {
                        inListing = true;
                        listingBuf.setLength(0);
                    }
                    listingBuf.append(line).append(System.lineSeparator());
                } else {
                    if (inListing) {
                        p.add(new Sentence(listingBuf.toString().trim()));
                        inListing = false;
                    }
                    String normalized = normalizeLine(line);
                    if (!normalized.isEmpty()) {
                        for (String sent : splitToSentences(normalized)) {
                            p.add(new Sentence(sent.trim()));
                        }
                    }
                }
            }
            if (inListing) {
                p.add(new Sentence(listingBuf.toString().trim()));
            }
            result.add(p);
        }
        return result;
    }

    private static List<String> splitToSentences(String line) {
        List<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            sb.append(c);
            if (c == '.' || c == '!' || c == '?') {
                while (i + 1 < line.length()) {
                    char nc = line.charAt(i + 1);
                    if (nc == '.' || nc == '!' || nc == '?') {
                        sb.append(nc);
                        i++;
                    } else break;
                }
                list.add(sb.toString().trim());
                sb.setLength(0);
                while (i + 1 < line.length() && line.charAt(i + 1) == ' ') i++;
            }
        }
        if (!sb.isEmpty()) {
            list.add(sb.toString().trim());
        }
        return list;
    }

    public static String process(String text, char startChar, char endChar) {
        List<Paragraph> paragraphs = parseParagraphs(text);
        StringBuilder out = new StringBuilder();
        boolean firstPara = true;
        for (Paragraph p : paragraphs) {
            if (!firstPara) out.append(System.lineSeparator()).append(System.lineSeparator());
            firstPara = false;
            for (Sentence s : p.getSentences()) {
                String processed = s.removeMaxSubstring(startChar, endChar);
               if (s.toString().contains("\n")) {
                    out.append(processed).append(System.lineSeparator());
                } else {
                    out.append(processed);
                    if (!processed.endsWith(System.lineSeparator())) out.append(" ");
                }
            }
        }
        return out.toString().trim();
    }
}
