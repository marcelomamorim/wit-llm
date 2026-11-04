/*
 * @test
 * @summary Verifies that the single-argument Locale.LanguageRange constructor delegates to the two-argument form with maximum weight and preserves the lowercased range.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {
    public static void main(String[] args) {
        Locale.LanguageRange r1 = new Locale.LanguageRange("en-US");
        check("en-us".equals(r1.getRange()), "range should be lowercased: " + r1.getRange());
        check(r1.getWeight() == 1.0, "default weight should be 1.0: " + r1.getWeight());

        Locale.LanguageRange r2 = new Locale.LanguageRange("ja");
        check("ja".equals(r2.getRange()), "range should be ja: " + r2.getRange());
        check(r2.getWeight() == 1.0, "default weight should be 1.0 for ja: " + r2.getWeight());

        Locale.LanguageRange r3 = new Locale.LanguageRange("*");
        check("*".equals(r3.getRange()), "wildcard range should be preserved: " + r3.getRange());
        check(r3.getWeight() == 1.0, "wildcard default weight should be 1.0: " + r3.getWeight());

        Locale.LanguageRange r4 = new Locale.LanguageRange("fr", 0.5);
        check("fr".equals(r4.getRange()), "explicit-weight range should be fr: " + r4.getRange());
        check(r4.getWeight() == 0.5, "explicit weight should be 0.5: " + r4.getWeight());
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
