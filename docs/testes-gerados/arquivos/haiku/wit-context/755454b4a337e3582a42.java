/*
 * @test
 * @summary Witup-derived checks for JapaneseEra.getDisplayName(TextStyle, Locale)
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class JapaneseEraWitupTest {

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    private static void testNonEmptyNamesForRegularEras() {
        JapaneseEra[] eras = { JapaneseEra.MEIJI, JapaneseEra.HEISEI };
        TextStyle[] styles = { TextStyle.FULL, TextStyle.SHORT, TextStyle.NARROW };
        for (JapaneseEra era : eras) {
            for (TextStyle style : styles) {
                String name = era.getDisplayName(style, Locale.US);
                check(name != null, "null name for " + era + " " + style);
                check(!name.isEmpty(), "empty name for " + era + " " + style);
            }
        }
    }

    private static void testDeterministicDisplayName() {
        String first = JapaneseEra.MEIJI.getDisplayName(TextStyle.FULL, Locale.US);
        String second = JapaneseEra.MEIJI.getDisplayName(TextStyle.FULL, Locale.US);
        check(first.equals(second), "display name not stable across calls");
    }

    private static void testValueOfRoundTripDisplayName() {
        JapaneseEra era = JapaneseEra.valueOf("MEIJI");
        check(era == JapaneseEra.MEIJI, "valueOf did not return MEIJI");
        String name = era.getDisplayName(TextStyle.FULL, Locale.US);
        check(name != null && !name.isEmpty(), "empty name after valueOf round trip");
    }

    private static void testNullStyleRejected() {
        boolean thrown = false;
        try {
            JapaneseEra.MEIJI.getDisplayName(null, Locale.US);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null style");
    }

    public static void main(String[] args) {
        testNonEmptyNamesForRegularEras();
        testDeterministicDisplayName();
        testValueOfRoundTripDisplayName();
        testNullStyleRejected();
    }
}
