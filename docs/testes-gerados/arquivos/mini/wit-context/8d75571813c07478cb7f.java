/*
 * @test
 * @summary Test java.util.Locale.LanguageRange constructor for exception and normal cases
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testNullRangeThrowsNPE();
        testWeightOutOfRangeThrowsIAE();
        testIllFormedRangeThrowsIAE();
        testValidRangeAndWeight();
    }

    private static void testNullRangeThrowsNPE() {
        try {
            new java.util.Locale.LanguageRange(null, 0.5);
            throw new RuntimeException("Expected NullPointerException for null range");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testWeightOutOfRangeThrowsIAE() {
        try {
            new java.util.Locale.LanguageRange("en", -0.1);
            throw new RuntimeException("Expected IllegalArgumentException for weight < 0");
        } catch (IllegalArgumentException expected) {
            // expected
        }
        try {
            new java.util.Locale.LanguageRange("en", 1.1);
            throw new RuntimeException("Expected IllegalArgumentException for weight > 1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testIllFormedRangeThrowsIAE() {
        // range ends with '-'
        try {
            new java.util.Locale.LanguageRange("en-", 0.5);
            throw new RuntimeException("Expected IllegalArgumentException for range ending with '-'");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // first subtag ill-formed (empty string)
        try {
            new java.util.Locale.LanguageRange("", 0.5);
            throw new RuntimeException("Expected IllegalArgumentException for empty first subtag");
        } catch (IllegalArgumentException expected) {
            // expected
        }

        // subsequent subtag ill-formed (empty subtag in middle)
        try {
            new java.util.Locale.LanguageRange("en--US", 0.5);
            throw new RuntimeException("Expected IllegalArgumentException for empty subtag in middle");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testValidRangeAndWeight() {
        java.util.Locale.LanguageRange lr = new java.util.Locale.LanguageRange("en-US", 0.75);
        if (!"en-us".equals(lr.getRange())) {
            throw new RuntimeException("Range should be lowercased to 'en-us', got: " + lr.getRange());
        }
        if (lr.getWeight() != 0.75) {
            throw new RuntimeException("Weight should be 0.75, got: " + lr.getWeight());
        }
    }
}
