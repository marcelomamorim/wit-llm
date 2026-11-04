/*
 * @test
 * @summary Test java.util.Locale.Builder.setLanguageTag(String) for valid and invalid inputs
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.Locale.Builder;
import java.util.IllformedLocaleException;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidLanguageTag();
        testInvalidLanguageTag();
        System.out.println("LocaleWitupTest passed.");
    }

    private static void testValidLanguageTag() {
        Builder builder = new Builder();
        // Valid language tag
        Builder result = builder.setLanguageTag("en-US");
        if (result == null) {
            throw new AssertionError("setLanguageTag returned null for valid input");
        }
        // The returned builder should be the same instance
        if (result != builder) {
            throw new AssertionError("setLanguageTag did not return the same Builder instance");
        }
    }

    private static void testInvalidLanguageTag() {
        Builder builder = new Builder();
        String invalidTag = "en-@-US"; // invalid tag with illegal character '@'
        try {
            builder.setLanguageTag(invalidTag);
            throw new AssertionError("Expected IllformedLocaleException was not thrown");
        } catch (IllformedLocaleException e) {
            // Expected exception, verify error index is non-negative
            if (e.getErrorIndex() < 0) {
                throw new AssertionError("IllformedLocaleException has negative error index");
            }
            if (e.getMessage() == null || e.getMessage().isEmpty()) {
                throw new AssertionError("IllformedLocaleException has empty error message");
            }
        }
    }
}
