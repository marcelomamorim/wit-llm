/*
 * @test
 * @summary Test java.util.Locale.LanguageRange constructor with single String argument
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        // Test constructor with a valid language range string
        java.util.Locale.LanguageRange lr = new java.util.Locale.LanguageRange("en-US");
        if (!"en-US".equals(lr.getRange())) {
            throw new RuntimeException("LanguageRange range mismatch: expected 'en-US' but got '" + lr.getRange() + "'");
        }
        if (lr.getWeight() != 1.0d) {
            throw new RuntimeException("LanguageRange weight mismatch: expected 1.0 but got " + lr.getWeight());
        }

        // Test constructor with empty string
        java.util.Locale.LanguageRange lrEmpty = new java.util.Locale.LanguageRange("");
        if (!"".equals(lrEmpty.getRange())) {
            throw new RuntimeException("LanguageRange range mismatch for empty string: expected '' but got '" + lrEmpty.getRange() + "'");
        }
        if (lrEmpty.getWeight() != 1.0d) {
            throw new RuntimeException("LanguageRange weight mismatch for empty string: expected 1.0 but got " + lrEmpty.getWeight());
        }

        // Test constructor with language range containing wildcard '*'
        java.util.Locale.LanguageRange lrWildcard = new java.util.Locale.LanguageRange("*");
        if (!"*".equals(lrWildcard.getRange())) {
            throw new RuntimeException("LanguageRange range mismatch for '*': expected '*' but got '" + lrWildcard.getRange() + "'");
        }
        if (lrWildcard.getWeight() != 1.0d) {
            throw new RuntimeException("LanguageRange weight mismatch for '*': expected 1.0 but got " + lrWildcard.getWeight());
        }
    }
}
