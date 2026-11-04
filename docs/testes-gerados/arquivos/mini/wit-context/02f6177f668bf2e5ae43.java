/*
 * @test
 * @summary Test java.util.Locale.Builder.removeUnicodeLocaleAttribute(String) for null and valid inputs
 * @run main LocaleWitupTest
 */

import java.util.Locale;
import java.util.Locale.Builder;
import java.util.IllformedLocaleException;

public class LocaleWitupTest {
    public static void main(String[] args) {
        testRemoveUnicodeLocaleAttributeNull();
        testRemoveUnicodeLocaleAttributeValid();
    }

    private static void testRemoveUnicodeLocaleAttributeNull() {
        Builder builder = new Builder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception for null attribute
        }
    }

    private static void testRemoveUnicodeLocaleAttributeValid() {
        Builder builder = new Builder();
        // Use a valid attribute string (non-null, non-empty, valid syntax)
        // Since the internal localeBuilder is not accessible, test that no exception is thrown
        // for a typical valid attribute string.
        try {
            builder.removeUnicodeLocaleAttribute("attribute");
        } catch (IllformedLocaleException e) {
            throw new RuntimeException("Unexpected IllformedLocaleException for valid attribute", e);
        }
    }
}
