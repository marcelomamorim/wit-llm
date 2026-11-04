/*
 * @test
 * @summary Test sun.util.locale.InternalLocaleBuilder.removeUnicodeLocaleAttribute(String) for valid and invalid inputs
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveUnicodeLocaleAttributeValid();
        testRemoveUnicodeLocaleAttributeNull();
        testRemoveUnicodeLocaleAttributeInvalid();
    }

    private static void testRemoveUnicodeLocaleAttributeValid() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Add a valid attribute to remove - since uattributes is private and no public add method,
        // we test that calling removeUnicodeLocaleAttribute with a valid attribute does not throw
        // and returns the builder itself.
        // We use a known valid attribute string "abc" (3 letters) which is valid per UnicodeLocaleExtension.isAttribute
        // Since we cannot add attributes publicly, we only test no exception and return value.
        InternalLocaleBuilder result = builder.removeUnicodeLocaleAttribute("abc");
        if (result != builder) {
            throw new AssertionError("removeUnicodeLocaleAttribute should return this");
        }
    }

    private static void testRemoveUnicodeLocaleAttributeNull() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            throw new AssertionError("Expected LocaleSyntaxException for null attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }

    private static void testRemoveUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Test with empty string
        try {
            builder.removeUnicodeLocaleAttribute("");
            throw new AssertionError("Expected LocaleSyntaxException for empty attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
        // Test with too short string (length < 3)
        try {
            builder.removeUnicodeLocaleAttribute("ab");
            throw new AssertionError("Expected LocaleSyntaxException for short attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
        // Test with invalid format string (non-attribute)
        try {
            builder.removeUnicodeLocaleAttribute("1234");
            throw new AssertionError("Expected LocaleSyntaxException for invalid attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }
}
