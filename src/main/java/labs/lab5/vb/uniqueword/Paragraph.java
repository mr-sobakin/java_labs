package labs.lab5.vb.uniqueword;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
    private final List<Sentence> sentences = new ArrayList<>();

    public void add(Sentence sentence) {
        sentences.add(sentence);
    }

    public List<Sentence> getSentences() {
        return sentences;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Sentence s : sentences) {
            sb.append(s.toString());
            if (s.toString().contains("\n")) sb.append(System.lineSeparator());
            else sb.append(" ");
        }
        return sb.toString().trim();
    }
}
