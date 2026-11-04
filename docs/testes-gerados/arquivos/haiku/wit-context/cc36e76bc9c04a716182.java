/*
 * @test
 * @summary WITUP-derived checks for InternalLocaleBuilder.removeUnicodeLocaleAttribute exception and chaining behavior
 * @modules java.base/sun.util.locale
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();

        expectSyntaxError(builder, null);
        expectSyntaxError(builder, "");
        expectSyntaxError(builder, "a");
        expectSyntaxError(builder, "ab");
        expectSyntaxError(builder, "abcdefghi");
        expectSyntaxError(builder, "ab!");

        InternalLocaleBuilder returned = builder.removeUnicodeLocaleAttribute("abc");
        if (returned != builder) {
            throw new RuntimeException("removeUnicodeLocaleAttribute must return this builder");
        }

        returned = builder.removeUnicodeLocaleAttribute("abc");
        if (returned != builder) {
            throw new RuntimeException("Removing an absent attribute must return this builder");
        }

        returned = builder.removeUnicodeLocaleAttribute("abcdefgh");
        if (returned != builder) {
            throw new RuntimeException("Maximum-length valid attribute must return this builder");
        }
    }

    private static void expectSyntaxError(InternalLocaleBuilder builder, String attribute) {
        try {
            builder.removeUnicodeLocaleAttribute(attribute);
            throw new RuntimeException("Expected LocaleSyntaxException for attribute: " + attribute);
        } catch (LocaleSyntaxException expected) {
            // expected
        }
    }
}
