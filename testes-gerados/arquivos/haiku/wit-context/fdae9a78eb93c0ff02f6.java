/*
 * @test
 * @summary Verifies the public LanguageRange(String) constructor: default MAX_WEIGHT, lowercase range normalization, and NullPointerException for null range.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        Locale.LanguageRange r = new Locale.LanguageRange("en-US");

        if (!"en-us".equals(r.getRange())) {
            throw new RuntimeException("Unexpected range: " + r.getRange());
        }
        if (r.getWeight() != Locale.LanguageRange.MAX_WEIGHT) {
            throw new RuntimeException("Unexpected weight: " + r.getWeight());
        }

        try {
            new Locale.LanguageRange(null);
            throw new RuntimeException("NullPointerException expected for null range");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
