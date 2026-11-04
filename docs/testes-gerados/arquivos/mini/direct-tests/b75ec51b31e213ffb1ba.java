/*
 * @test
 * @summary Test java.util.Locale.Builder.setLanguageTag behavior
 * @run main LocaleWitupTest
 */

public class LocaleWitupTest {
    public static void main(String[] args) {
        testValidLanguageTag();
        testInvalidLanguageTag();
    }

    private static void testValidLanguageTag() {
        java.util.Locale.Builder builder = new java.util.Locale.Builder();
        // Valid language tag
        String tag = "en-US";
        builder.setLanguageTag(tag);
        java.util.Locale locale = builder.build();
        if (!"en".equals(locale.getLanguage())) {
            throw new RuntimeException("Expected language 'en' but got: " + locale.getLanguage());
        }
        if (!"US".equals(locale.getCountry())) {
            throw new RuntimeException("Expected country 'US' but got: " + locale.getCountry());
        }
    }

    private static void testInvalidLanguageTag() {
        java.util.Locale.Builder builder = new java.util.Locale.Builder();
        String invalidTag = "invalid_tag!";
        try {
            builder.setLanguageTag(invalidTag);
            throw new RuntimeException("Expected IllformedLocaleException for invalid tag");
        } catch (java.util.IllformedLocaleException e) {
            // Expected exception
            if (e.getMessage() == null || e.getMessage().isEmpty()) {
                throw new RuntimeException("Exception message should not be empty");
            }
            if (e.getErrorIndex() < 0) {
                throw new RuntimeException("Exception error index should be >= 0");
            }
        }
    }
}
