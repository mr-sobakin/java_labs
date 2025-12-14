package labs.lab5.vb.uniqueword;

import java.util.ArrayList;
import java.util.List;

public class Paragraph {
    private final List<Object> parts = new ArrayList<>(); // Sentence or Listing

    public void add(Object part) {
        parts.add(part);
    }

    public List<Object> getParts() {
        return parts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object o : parts) {
            sb.append(o.toString());
            if (o instanceof Sentence) sb.append(" ");
            else sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
