/*
 * @test    @summary Test for Locale.LanguageRange constructor with valid and invalid inputs
 * @run main LocaleWitupTest
 */

import java.util.Locale;

public class LocaleWitupTest {

    public static void main(String[] args) {
        testValidLanguageRange();
        testNullRange();
        testInvalidWeightLow();
        testInvalidWeightHigh();
        testIllFormedRange();
        testRangeEndingWithHyphen();
    }

    static void testValidLanguageRange() {
        try {
            Locale.LanguageRange lr = new Locale.LanguageRange("en-US", 0.5);
            assert lr != null : "Locale.LanguageRange should not be null for valid input";
            assert "en-us".equals(lr.range) : "Range should be lowercased";
            assert lr.weight == 0.5 : "Weight should be preserved";
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testValidLanguageRange", e);
        }
    }

    static void testNullRange() {
        try {
            new Locale.LanguageRange(null, 0.5);
            throw new AssertionError("NullPointerException expected for null range");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testInvalidWeightLow() {
        try {
            new Locale.LanguageRange("en", -0.1);
            throw new AssertionError("IllegalArgumentException expected for weight < MIN_WEIGHT");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testInvalidWeightHigh() {
        try {
            new Locale.LanguageRange("en", 1.1);
            throw new AssertionError("IllegalArgumentException expected for weight > MAX_WEIGHT");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testIllFormedRange() {
        try {
            new Locale.LanguageRange("en--US", 0.5);
            throw new AssertionError("IllegalArgumentException expected for ill-formed range");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testRangeEndingWithHyphen() {
        try {
            new Locale.LanguageRange("en-", 0.5);
            throw new AssertionError("IllegalArgumentException expected for range ending with hyphen");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
