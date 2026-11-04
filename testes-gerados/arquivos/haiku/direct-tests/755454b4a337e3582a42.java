/*
 * @test
 * @summary Verifies JapaneseEra.getDisplayName for standard eras, determinism, and null style handling
 * @run main JapaneseEraWitupTest
 */

import java.time.chrono.JapaneseEra;
import java.time.format.TextStyle;
import java.util.Locale;

public class JapaneseEraWitupTest {

    public static void main(String[] args) {
        JapaneseEra[] eras = { JapaneseEra.MEIJI, JapaneseEra.TAISHO, JapaneseEra.SHOWA, JapaneseEra.HEISEI };
        TextStyle[] styles = { TextStyle.FULL, TextStyle.SHORT, TextStyle.NARROW };

        for (JapaneseEra era : eras) {
            for (TextStyle style : styles) {
                String name = era.getDisplayName(style, Locale.US);
                check(name != null && !name.isEmpty(),
                      "empty display name for " + era + " " + style);
                check(name.equals(era.getDisplayName(style, Locale.US)),
                      "non-deterministic display name for " + era + " " + style);
            }
        }

        JapaneseEra heisei = JapaneseEra.of(JapaneseEra.HEISEI.getValue());
        check(heisei == JapaneseEra.HEISEI, "of(getValue()) should return the same constant");
        check(heisei.getDisplayName(TextStyle.FULL, Locale.US)
                .equals(JapaneseEra.HEISEI.getDisplayName(TextStyle.FULL, Locale.US)),
              "display name mismatch after of() round trip");

        boolean npe = false;
        try {
            JapaneseEra.MEIJI.getDisplayName(null, Locale.US);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null TextStyle should raise NullPointerException for MEIJI");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
