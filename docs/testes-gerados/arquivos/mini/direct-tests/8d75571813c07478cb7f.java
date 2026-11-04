/*
 * @test
 * @summary Test java.util.Locale.LanguageRange constructor with various inputs
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidLanguageRange();
        testNullRangeThrowsNPE();
        testWeightBelowMinThrowsIAE();
        testWeightAboveMaxThrowsIAE();
        testIllFormedRangeThrowsIAE();
        testRangeEndsWithDashThrowsIAE();
        testIllFormedSubtagThrowsIAE();
    }

    private static void testValidLanguageRange() {
        java.util.Locale.LanguageRange lr = new java.util.Locale.LanguageRange("en-US", 0.5);
        if (!"en-us".equals(lr.getRange())) {
            throw new AssertionError("Expected range 'en-us' but got: " + lr.getRange());
        }
        if (lr.getWeight() != 0.5) {
            throw new AssertionError("Expected weight 0.5 but got: " + lr.getWeight());
        }
    }

    private static void testNullRangeThrowsNPE() {
        try {
            new java.util.Locale.LanguageRange(null, 0.5);
            throw new AssertionError("Expected NullPointerException for null range");
        } catch (NullPointerException expected) {
        }
    }

    private static void testWeightBelowMinThrowsIAE() {
        try {
            new java.util.Locale.LanguageRange("en", -0.1);
            throw new AssertionError("Expected IllegalArgumentException for weight below min");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testWeightAboveMaxThrowsIAE() {
        try {
            new java.util.Locale.LanguageRange("en", 1.1);
            throw new AssertionError("Expected IllegalArgumentException for weight above max");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testIllFormedRangeThrowsIAE() {
        try {
            new java.util.Locale.LanguageRange("en_Us", 0.5); // underscore not allowed
            throw new AssertionError("Expected IllegalArgumentException for ill-formed range");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testRangeEndsWithDashThrowsIAE() {
        try {
            new java.util.Locale.LanguageRange("en-", 0.5);
            throw new AssertionError("Expected IllegalArgumentException for range ending with dash");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testIllFormedSubtagThrowsIAE() {
        try {
            new java.util.Locale.LanguageRange("en-u$", 0.5); // illegal character in subtag
            throw new AssertionError("Expected IllegalArgumentException for ill-formed subtag");
        } catch (IllegalArgumentException expected) {
        }
    }
}