/*
 * @test
 * @summary Tests for java.util.Locale class focusing on the LanguageRange constructor.
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testLanguageRangeNullRange();
        testLanguageRangeInvalidWeight();
        testLanguageRangeValid();
    }

    private static void testLanguageRangeNullRange() {
        boolean exceptionThrown = false;
        try {
            new Locale.LanguageRange(null, 0.5);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException for null range");
        }
    }

    private static void testLanguageRangeInvalidWeight() {
        boolean exceptionThrownHigh = false;
        boolean exceptionThrownLow = false;
        try {
            new Locale.LanguageRange("en", 1.5);
        } catch (IllegalArgumentException e) {
            exceptionThrownHigh = true;
        }
        try {
            new Locale.LanguageRange("en", -0.1);
        } catch (IllegalArgumentException e) {
            exceptionThrownLow = true;
        }
        if (!exceptionThrownHigh) {
            throw new AssertionError("Expected IllegalArgumentException for weight > 1.0");
        }
        if (!exceptionThrownLow) {
            throw new AssertionError("Expected IllegalArgumentException for weight < 0.0");
        }
    }

    private static void testLanguageRangeValid() {
        Locale.LanguageRange lr = new Locale.LanguageRange("en-US", 0.8);
        if (lr == null || !lr.range.equals("en-us") || lr.weight != 0.8) {
            throw new AssertionError("Valid LanguageRange did not initialize correctly");
        }
    }
}
