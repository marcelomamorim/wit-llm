/* @test    @summary Test InternalLocaleBuilder.addUnicodeLocaleAttribute for valid and invalid inputs    @run main InternalLocaleBuilderWitupTest_P15d322933f80 */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest_P15d322933f80 {

    public static void main(String[] args) {
        testAddUnicodeLocaleAttributeValid();
        testAddUnicodeLocaleAttributeInvalid();
    }

    static void testAddUnicodeLocaleAttributeValid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.addUnicodeLocaleAttribute("foo");
            builder.addUnicodeLocaleAttribute("bar");
            System.out.println("Valid attributes added successfully.");
        } catch (LocaleSyntaxException e) {
            throw new RuntimeException("Unexpected exception for valid attributes", e);
        }
    }

    static void testAddUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        boolean exceptionThrown = false;
        try {
            builder.addUnicodeLocaleAttribute("invalid-attribute!");
        } catch (LocaleSyntaxException e) {
            exceptionThrown = true;
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        if (!exceptionThrown) {
            throw new RuntimeException("Expected LocaleSyntaxException was not thrown for invalid attribute.");
        }
    }
}
