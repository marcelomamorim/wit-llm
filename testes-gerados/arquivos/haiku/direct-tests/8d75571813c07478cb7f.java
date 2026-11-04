/*
 * @test
 * @summary Verifies argument validation of Locale.LanguageRange(String, double)
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void expectNPE(String range, double weight, String msg) {
        try {
            new Locale.LanguageRange(range, weight);
            check(false, msg + " (no exception)");
        } catch (NullPointerException e) {
            // expected
        } catch (RuntimeException e) {
            check(false, msg + " (wrong exception: " + e + ")");
        }
    }

    static void expectIAE(String range, double weight, String msg) {
        try {
            new Locale.LanguageRange(range, weight);
            check(false, msg + " (no exception)");
        } catch (IllegalArgumentException e) {
            // expected
        } catch (RuntimeException e) {
            check(false, msg + " (wrong exception: " + e + ")");
        }
    }

    static void expectOK(String range, double weight, String msg) {
        try {
            new Locale.LanguageRange(range, weight);
        } catch (RuntimeException e) {
            check(false, msg + " (unexpected exception: " + e + ")");
        }
    }

    public static void main(String[] args) {
        // null range
        expectNPE(null, 0.5, "null range");

        // weight out of bounds
        expectIAE("en", -0.1, "weight below MIN_WEIGHT");
        expectIAE("en", 1.1, "weight above MAX_WEIGHT");

        // ill-formed ranges
        expectIAE("en-", 0.5, "trailing hyphen");
        expectIAE("abcdefghi", 0.5, "language subtag longer than 8 letters");

        // valid inputs and weight boundaries must not throw
        expectOK("en", 0.0, "minimum weight boundary");
        expectOK("en", 1.0, "maximum weight boundary");
        expectOK("en-US", 0.5, "language-country range");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
