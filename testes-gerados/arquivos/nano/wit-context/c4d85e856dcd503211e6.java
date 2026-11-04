/*
 * @test
 * @summary Tests for InternalLocaleBuilder.addUnicodeLocaleAttribute
 * @run main InternalLocaleBuilderWitupTest
 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) throws Exception {
        testAddUnicodeLocaleAttributeValid();
        testAddUnicodeLocaleAttributeEmpty();
        testAddUnicodeLocaleAttributeInvalid();
    }

    private static void testAddUnicodeLocaleAttributeValid() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String validAttribute = "abc";
        builder.addUnicodeLocaleAttribute(validAttribute);
        System.out.println("Valid attribute accepted.");
    }

    private static void testAddUnicodeLocaleAttributeEmpty() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String emptyAttribute = "";
        try {
            builder.addUnicodeLocaleAttribute(emptyAttribute);
            System.out.println("Empty attribute accepted (unexpected).");
        } catch (LocaleSyntaxException e) {
            System.out.println("Empty attribute correctly threw LocaleSyntaxException.");
        }
    }

    private static void testAddUnicodeLocaleAttributeInvalid() throws Exception {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String invalidAttribute = "Invalid*";
        try {
            builder.addUnicodeLocaleAttribute(invalidAttribute);
            System.out.println("Invalid attribute accepted (unexpected).");
        } catch (LocaleSyntaxException e) {
            System.out.println("Invalid attribute correctly threw LocaleSyntaxException.");
        }
    }
}
