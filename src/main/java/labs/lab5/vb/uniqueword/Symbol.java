package labs.lab5.vb.uniqueword;

public class Symbol {
    private final char ch;

    public Symbol(char ch) {
        this.ch = ch;
    }

    public char getChar() {
        return ch;
    }

    @Override
    public String toString() {
        return Character.toString(ch);
    }
}
