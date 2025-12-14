package labs.lab5.vb.uniqueword;

public class Sentence {
    private final String text;

    public Sentence(String text) {
        this.text = text;
    }

    public String removeMaxSubstring(char startChar, char endChar) {
        int first = text.indexOf(startChar);
        if (first == -1) return text;
        int last = text.lastIndexOf(endChar);
        if (last == -1 || last < first) {
            int next = text.indexOf(endChar, first + 1);
            if (next == -1) return text;
            last = next;
        }
        String result = text.substring(0, first) + text.substring(last + 1);

        return result.replaceAll(" {2,}", " ").trim().isEmpty() ? result.trim() : result;
    }

    @Override
    public String toString() {
        return text;
    }
}
