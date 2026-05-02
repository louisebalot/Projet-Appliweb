package pack.outils;

import java.text.Normalizer;

public class StringNormalizer {
    /**
     * Normalise une chaîne de caractères.
     * Enlève les accents et met le mot en majuscules
     *
     * @param string chaîne de caractères à normaliser.
     * @return chaîne de caractères normalisée.
     */
    public static String normaliserString(String string) {
        return Normalizer.normalize(string.toUpperCase(), Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "");
    }
}
