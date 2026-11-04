/*
 * @test
 * @summary Test sun.util.locale.InternalLocaleBuilder.removeUnicodeLocaleAttribute method
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {
    public static void main(String[] args) throws Exception {
        testRemoveUnicodeLocaleAttributeNull();
        testRemoveUnicodeLocaleAttributeInvalid();
        testRemoveUnicodeLocaleAttributeValid();
    }

    private static void testRemoveUnicodeLocaleAttributeNull() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            throw new RuntimeException("Expected LocaleSyntaxException for null attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }

    private static void testRemoveUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.removeUnicodeLocaleAttribute("invalid-attribute");
            throw new RuntimeException("Expected LocaleSyntaxException for invalid attribute");
        } catch (LocaleSyntaxException e) {
            // expected
        }
    }

    private static void testRemoveUnicodeLocaleAttributeValid() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        // Add attribute by reflection since no public method to add
        // But reflection is disallowed by instructions, so we test removal on null uattributes
        // The method should not throw if uattributes is null
        InternalLocaleBuilder result = builder.removeUnicodeLocaleAttribute("abcde");
        if (result != builder) {
            throw new RuntimeException("removeUnicodeLocaleAttribute should return this");
        }
    }
}
