/* @test    @summary Test for InternalLocaleBuilder.removeUnicodeLocaleAttribute with null input and invalid attribute */
/* @run main InternalLocaleBuilderWitupTest */

import sun.util.locale.InternalLocaleBuilder;
import sun.util.locale.LocaleSyntaxException;

public class InternalLocaleBuilderWitupTest {

    public static void main(String[] args) {
        testRemoveUnicodeLocaleAttributeNull();
        testRemoveUnicodeLocaleAttributeInvalid();
    }

    private static void testRemoveUnicodeLocaleAttributeNull() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        try {
            builder.removeUnicodeLocaleAttribute(null);
            System.out.println("PASS: removeUnicodeLocaleAttribute(null) did not throw");
        } catch (LocaleSyntaxException e) {
            System.out.println("FAIL: removeUnicodeLocaleAttribute(null) threw LocaleSyntaxException");
        } catch (Exception e) {
            System.out.println("FAIL: removeUnicodeLocaleAttribute(null) threw unexpected exception: " + e);
        }
    }

    private static void testRemoveUnicodeLocaleAttributeInvalid() {
        InternalLocaleBuilder builder = new InternalLocaleBuilder();
        String invalidAttribute = "InvalidAttribute";
        try {
            builder.removeUnicodeLocaleAttribute(invalidAttribute);
            System.out.println("FAIL: removeUnicodeLocaleAttribute(invalidAttribute) did not throw");
        } catch (LocaleSyntaxException e) {
            System.out.println("PASS: removeUnicodeLocaleAttribute(invalidAttribute) threw LocaleSyntaxException");
        } catch (Exception e) {
            System.out.println("FAIL: removeUnicodeLocaleAttribute(invalidAttribute) threw unexpected exception: " + e);
        }
    }
}
