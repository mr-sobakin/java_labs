package labs.lab4.v7.lambdas;

public class TextProcessor {
    public static int MAX_WORDS = 8;

    public static final TriFunction<String, Integer, Integer, String> PROCESSOR =
            (text, copies, limit) -> {
                if (text == null) throw new IllegalArgumentException("text must not be null");
                if (copies == null || copies <= 0) throw new IllegalArgumentException("copies must be > 0");
                if (limit == null || limit < 0) throw new IllegalArgumentException("limit must be >= 0");

                String trimmed = text.trim();
                if (trimmed.isEmpty() || limit == 0) return "";

                String[] words = trimmed.split("\\s+");
                StringBuilder result = new StringBuilder();
                int producedWords = 0;

                boolean stop = false;
                for (int c = 0; c < copies; c++) {
                    for (String w : words) {
                        if (producedWords >= MAX_WORDS) {
                            stop = true;
                            break;
                        }
                        String token = w + ",";
                        String tokenWithSep = result.isEmpty() ? token : " " + token;
                        if (result.length() + tokenWithSep.length() > limit) {
                            stop = true;
                            break;
                        }
                        result.append(tokenWithSep);
                        producedWords++;
                    }
                    if (stop) break;
                }
                if (result.length() > 0 && result.charAt(result.length() - 1) == ',') {
                    result.deleteCharAt(result.length() - 1);
                }
                return result.toString();
            };
}
