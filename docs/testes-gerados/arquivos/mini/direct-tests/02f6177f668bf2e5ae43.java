/*
 * @test
 * @summary Test java.util.Locale.Builder.removeUnicodeLocaleAttribute(String) behavior
 * @run main java.util.LocaleWitupTest
 */

import java.util.Locale;
import java.util.Locale.Builder;
import java.util.IllformedLocaleException;
import java.util.Objects;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testRemoveUnicodeLocaleAttributeNull();
        testRemoveUnicodeLocaleAttributeValid();
        testRemoveUnicodeLocaleAttributeInvalid();
    }

    private static void testRemoveUnicodeLocaleAttributeNull() {
        Builder builder = new Builder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            throw new RuntimeException("Expected NullPointerException for null attribute");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testRemoveUnicodeLocaleAttributeValid() {
        Builder builder = new Builder();
        builder.setLanguage("en");
        builder.setRegion("US");
        builder.setUnicodeLocaleKeyword("ca", "buddhist");
        builder.removeUnicodeLocaleAttribute("ca"); // 'ca' is a key, not attribute, but allowed to remove
        // No exception expected, builder returned is same instance
        if (builder.removeUnicodeLocaleAttribute("ca") != builder) {
            throw new RuntimeException("removeUnicodeLocaleAttribute should return this");
        }
    }

    private static void testRemoveUnicodeLocaleAttributeInvalid() {
        Builder builder = new Builder();
        // Use an invalid attribute string that should cause LocaleSyntaxException internally
        // According to LocaleSyntaxException docs, attribute must be alphanumeric and length 3-8
        // Use invalid attribute with invalid characters or length
        String invalidAttribute = "@invalid!";
        try {
            builder.removeUnicodeLocaleAttribute(invalidAttribute);
            throw new RuntimeException("Expected IllformedLocaleException for invalid attribute");
        } catch (IllformedLocaleException ile) {
            // expected
            if (!ile.getMessage().contains(invalidAttribute)) {
                throw new RuntimeException("Exception message should contain invalid attribute");
            }
            if (ile.getErrorIndex() < 0) {
                throw new RuntimeException("Error index should be non-negative");
            }
        }
    }
}
