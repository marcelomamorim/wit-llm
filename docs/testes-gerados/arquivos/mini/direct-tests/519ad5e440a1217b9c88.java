/*
 * @test
 * @summary Test SimpleDateFormat.applyLocalizedPattern(String) with various patterns
 * @run main SimpleDateFormatWitupTest
 */

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleDateFormatWitupTest {
    public static void main(String[] args) throws Exception {
        testApplyLocalizedPatternBasic();
        testApplyLocalizedPatternEmpty();
        testApplyLocalizedPatternSpecialChars();
    }

    static void testApplyLocalizedPatternBasic() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String localizedPattern = "d/M/y"; // typical localized pattern
        sdf.applyLocalizedPattern(localizedPattern);
        // The pattern field is set to the translated pattern
        String pattern = sdf.toPattern();
        if (pattern == null || pattern.isEmpty()) {
            throw new RuntimeException("Pattern should not be null or empty after applyLocalizedPattern");
        }
        // The pattern should not be equal to the localized pattern (since translation occurs)
        if (pattern.equals(localizedPattern)) {
            throw new RuntimeException("Pattern should be translated and differ from localized pattern");
        }
    }

    static void testApplyLocalizedPatternEmpty() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        String localizedPattern = ""; // empty pattern
        sdf.applyLocalizedPattern(localizedPattern);
        String pattern = sdf.toPattern();
        if (pattern == null) {
            throw new RuntimeException("Pattern should not be null after applying empty localized pattern");
        }
    }

    static void testApplyLocalizedPatternSpecialChars() {
        SimpleDateFormat sdf = new SimpleDateFormat();
        // Pattern with quotes and special characters
        String localizedPattern = "'at' HH:mm:ss z";
        sdf.applyLocalizedPattern(localizedPattern);
        String pattern = sdf.toPattern();
        if (pattern == null || pattern.isEmpty()) {
            throw new RuntimeException("Pattern should not be null or empty after applying special chars pattern");
        }
        // The pattern should contain the literal text "at"
        if (!pattern.contains("at")) {
            throw new RuntimeException("Pattern should contain literal text 'at'");
        }
    }
}
