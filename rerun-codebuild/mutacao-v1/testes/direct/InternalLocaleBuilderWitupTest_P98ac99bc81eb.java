/* @test    @summary Test InternalLocaleBuilder.addUnicodeLocaleAttribute() method for valid and invalid inputs    @run main InternalLocaleBuilderWitupTest_P98ac99bc81eb */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest_P98ac99bc81eb {

    public static void main(String[] args) {
        testAddUnicodeLocaleAttributeValid();
        testAddUnicodeLocaleAttributeInvalid();
    }

    private static void testAddUnicodeLocaleAttributeValid() {
        try {
            InternalLocaleBuilder builder = new InternalLocaleBuilder();
            builder.addUnicodeLocaleAttribute("abc");
            builder.addUnicodeLocaleAttribute("XYZ");
            builder.addUnicodeLocaleAttribute("123");
        } catch (LocaleSyntaxException e) {
            throw new AssertionError("Unexpected exception for valid attributes", e);
        }
    }

    private static void testAddUnicodeLocaleAttributeInvalid() {
        boolean thrown = false;
        try {
            InternalLocaleBuilder builder = new InternalLocaleBuilder();
            builder.addUnicodeLocaleAttribute("Invalid Attribute!");
        } catch (LocaleSyntaxException e) {
            thrown = true;
            if (!e.getMessage().contains("Ill-formed Unicode locale attribute")) {
                throw new AssertionError("Incorrect exception message", e);
            }
        }
        if (!thrown) {
            throw new AssertionError("Expected LocaleSyntaxException was not thrown for invalid attribute");
        }
    }
}
