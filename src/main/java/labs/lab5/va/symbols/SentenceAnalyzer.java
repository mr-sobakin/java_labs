package labs.lab5.va.symbols;

public final class SentenceAnalyzer {

    private static final String VOWELS =
            "aeiouyAEIOUY" +
                    "аеёиоуыэюяАЕЁИОУЫЭЮЯ";

    private SentenceAnalyzer() { }

    public static Analysis analyze(String sentence) {
        if (sentence == null) sentence = "";
        int vowels = 0;
        int consonants = 0;
        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (Character.isLetter(ch)) {
                if (VOWELS.indexOf(ch) >= 0) vowels++;
                else consonants++;
            }
        }
        Verdict verdict;
        if (vowels > consonants) verdict = Verdict.MORE_VOWELS;
        else if (consonants > vowels) verdict = Verdict.MORE_CONSONANTS;
        else verdict = Verdict.EQUAL;
        return new Analysis(vowels, consonants, verdict);
    }

    public static final class Analysis {
        public final int vowels;
        public final int consonants;
        public final Verdict verdict;

        public Analysis(int vowels, int consonants, Verdict verdict) {
            this.vowels = vowels;
            this.consonants = consonants;
            this.verdict = verdict;
        }
    }

    public enum Verdict {
        MORE_VOWELS, MORE_CONSONANTS, EQUAL
    }
}
