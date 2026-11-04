/*
 * @test
 * @summary Verify LanguageRange constructor exception behavior for null range, out-of-range weight, ill-formed range and valid boundary inputs
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testNullRangeThrowsNPE();
        testWeightBelowMinThrowsIAE();
        testWeightAboveMaxThrowsIAE();
        testIllFormedTrailingDashThrowsIAE();
        testValidBoundaryWeightsAccepted();
    }

    static void testNullRangeThrowsNPE() {
        try {
            new Locale.LanguageRange(null, 0.5);
            throw new AssertionError("expected NullPointerException for null range");
        } catch (NullPointerException e) {
        }
    }

    static void testWeightBelowMinThrowsIAE() {
        try {
            new Locale.LanguageRange("en", -0.1);
            throw new AssertionError("expected IllegalArgumentException for weight < 0");
        } catch (IllegalArgumentException e) {
        }
    }

    static void testWeightAboveMaxThrowsIAE() {
        try {
            new Locale.LanguageRange("en", 1.1);
            throw new AssertionError("expected IllegalArgumentException for weight > 1");
        } catch (IllegalArgumentException e) {
        }
    }

    static void testIllFormedTrailingDashThrowsIAE() {
        try {
            new Locale.LanguageRange("en-", 0.5);
            throw new AssertionError("expected IllegalArgumentException for range ending with '-'");
        } catch (IllegalArgumentException e) {
        }
    }

    static void testValidBoundaryWeightsAccepted() {
        new Locale.LanguageRange("en", 0.0);
        new Locale.LanguageRange("en-US", 1.0);
        new Locale.LanguageRange("EN-us", 0.5);
    }
}
